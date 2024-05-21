package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.attender.service.AttenderService;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.meeting.dto.MeetingDTO;
import team.broadcast.domain.meeting.exception.MeetingErrorCode;
import team.broadcast.domain.meeting.service.MeetingService;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.dto.RoomResponse;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResult;
import team.broadcast.domain.video_room.entity.Room;
import team.broadcast.domain.video_room.exception.RoomErrorCode;
import team.broadcast.domain.video_room.repository.RoomMemoryRepository;
import team.broadcast.global.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private static final String ROOM_ADDRESS = "http://localhost:8080/api/room/";

    private final JanusClient janusClient;
    private final RoomMemoryRepository roomMemoryRepository;
    private final AttenderService attenderService;
    private final UserService userService;
    private final MeetingService meetingService;

    // janus webrtc 에러가 있는지 체크한다.
    private VideoRoomResponse checkExceptionResponse(Mono<VideoRoomResponse> responseMono) throws Exception {
        VideoRoomResponse response = responseMono.block();

        if (response == null) {
            throw new IllegalAccessException("Response Not Found");
        }

        if (response.isError()) {
            throw new JanusError(response.getError());
        }

        if (response.isPluginError()) {
            throw new JanusError(response.getResponse().getError_code(), response.getResponse().getError());
        }

        return response;
    }

    // 방 초대 링크 생성
    public String inviteLink(Long videoRoomId, Long userId) {
        Room room = roomMemoryRepository.findById(videoRoomId);

        // 방이 존재하지 않으면 에러 메시지를 보낸다.
        if (room == null) {
            throw new CustomException(RoomErrorCode.ROOM_NOT_FOUND);
        }

        // 초대 링크를 생성하는 사람이 회의 추최자야 한다.
        Attender attender = attenderService.getAttenderByUserIdAndMeetingId(userId, room.getMeetingId());

        if (!attender.isHost()) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }
        return ROOM_ADDRESS + videoRoomId;
    }

    // 1. 비디오 생성
    @Transactional
    public RoomResponse createRoom(Long meetingId, String email, VideoRoomCreate request) throws Exception {
        User user = userService.findUserByEmail(email);

        Attender attender = attenderService.getAttenderByUserIdAndMeetingId(user.getId(), meetingId);

        if (!attender.isHost()) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }

        List<Attender> participants = new ArrayList<>();
        participants.add(attender);

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        Room room = Room.builder()
                .id(response.getRoom())
                .name(request.getDisplay())
                .participants(participants)
                .meetingId(meetingId)
                .build();

        roomMemoryRepository.save(room);

        return toDto(room);
    }

    // 방 입장
    public void joinRoom(Long roomId, User user) {
        Room room = roomMemoryRepository.findById(roomId);

        if (room == null) {
            throw new CustomException(RoomErrorCode.ROOM_NOT_FOUND);
        }
        Attender attender = null;
        try {
            attender = attenderService.getAttenderByUserIdAndMeetingId(user.getId(), room.getMeetingId());
        } catch (CustomException e) {
            MeetingDTO meeting = meetingService.findMeetingById(room.getMeetingId());
            attender = Attender.builder()
                    .user(user)
                    .meeting(meeting.toEntity())
                    .build();
        }

        attenderService.addAttender(AttenderDTO.toDTO(attender));

        List<Attender> participants = room.getParticipants();
        participants.add(attender);

        room.updateParticipants(participants);

        roomMemoryRepository.save(room);
    }

    public RoomResponse toDto(Room room) {
        return RoomResponse.builder()
                .name(room.getName())
                .currentCount(room.getCurrentCount())
                .maxCount(room.getMaxCount())
                // 해당 코드가 맞는지 먼저 생각을 한다.
                .participants(room.getParticipants().stream().map(AttenderDTO::toDTO).toList())
                .build();
    }


    // 2. 비디오 삭제
    @Transactional
    public void destroyRoom(User user, VideoRoomDestroyRequest request) throws Exception {


        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        Room room = roomMemoryRepository.findById(response.getRoom());

        if (room == null) {
            throw new IllegalAccessException("Destroy Error");
        }

        Attender attender = attenderService.getAttenderByUserIdAndMeetingId(user.getId(), room.getMeetingId());

        // 호스트인 경우에만 삭제가 가능하다.
        if (!attender.isHost()) {
            throw new CustomException(MeetingErrorCode.ALLOW_HOST_ROLE);
        }

        roomMemoryRepository.delete(room.getId());
    }

    public Room findByRoomId(Long roomId) {
        return roomMemoryRepository.findById(roomId);
    }
}
