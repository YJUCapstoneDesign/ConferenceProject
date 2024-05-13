package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.attender.exception.AttenderErrorCode;
import team.broadcast.domain.attender.mysql.repository.AttenderRepository;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomEditRequest;
import team.broadcast.domain.video_room.dto.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.response.VideoRoomResult;
import team.broadcast.domain.video_room.exception.RoomErrorCode;
import team.broadcast.domain.video_room.repository.VideoRoomRepository;
import team.broadcast.global.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRoomService {

    private static final String ROOM_ADDRESS = "http://localhost:8080/api/room/";

    private final JanusClient janusClient;
    private final VideoRoomRepository videoRoomRepository;
    private final AttenderRepository attenderRepository;
    private final UserRepository userRepository;

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
        VideoRoom room = videoRoomRepository.findById(videoRoomId);

        // 방이 존재하지 않으면 에러 메시지를 보낸다.
        if (room == null) {
            throw new CustomException(RoomErrorCode.ROOM_NOT_FOUND);
        }

        // 초대 링크를 생성하는 사람이 회의 추최자야 한다.
        List<Attender> attender = attenderRepository.findByUserId(userId);
        if (attender.isEmpty()) {
            throw new CustomException(AttenderErrorCode.ATTENDER_NOT_FOUND);
        }

        return ROOM_ADDRESS + videoRoomId;
    }

    // 1. 비디오 생성
    @Transactional
    public VideoRoom createRoom(String email, VideoRoomCreate request) throws Exception {
        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        List<AttenderDTO> arr = new ArrayList<>();
        arr.add(new AttenderDTO(user.getId(), request.getRoom(), MeetingRole.HOST));

        VideoRoomResult response = block.getResponse();

        VideoRoom room = VideoRoom.builder()
                .roomId(response.getRoom())
                .roomName(request.getDisplay())
                .publisherId(user.getId())
                .participants(arr)
                .build();

        videoRoomRepository.save(room);

        return room;
    }

    // 2. 비디오 수정
    @Transactional
    public VideoRoom updateRoom(VideoRoomEditRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        VideoRoom updateRoom = VideoRoom.builder()
                .roomId(response.getRoom())
                .roomName(request.getNewDescription())
                .build();

        videoRoomRepository.update(updateRoom);

        return updateRoom;
    }

    // 3. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest request) throws Exception {


        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        VideoRoom room = videoRoomRepository.findById(response.getRoom());

        if (room == null) {
            throw new IllegalAccessException("Destroy Error");
        }

        videoRoomRepository.delete(room.getRoomId());
    }

    public VideoRoom findByRoomId(Long roomId) {
        return videoRoomRepository.findById(roomId);
    }
}
