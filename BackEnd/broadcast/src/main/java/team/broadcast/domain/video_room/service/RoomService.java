package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.domain.video_room.dto.RoomResponse;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResult;
import team.broadcast.domain.video_room.entity.Room;
import team.broadcast.domain.video_room.repository.RoomMemoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private static final String ROOM_ADDRESS = "http://localhost:8080/api/room/";

    private final JanusClient janusClient;
    private final RoomMemoryRepository roomMemoryRepository;
    private final UserService userService;

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


    // 1. 비디오 생성
    @Transactional
    public RoomResponse createRoom(Long meetingId, String email, VideoRoomCreate request) throws Exception {
        User user = userService.findUserByEmail(email);

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        Room room = Room.builder()
                .id(response.getRoom())
                .name(request.getDisplay())
//                .participants(participants)
                .meetingId(meetingId)
                .build();

        roomMemoryRepository.save(room);

        return toDto(room);
    }

    public RoomResponse toDto(Room room) {
        return RoomResponse.builder()
                .name(room.getName())
                .currentCount(room.getCurrentCount())
                .maxCount(room.getMaxCount())
                // 해당 코드가 맞는지 먼저 생각을 한다.
//                .participants(room.getParticipants().stream().map(AttenderDTO::toDTO).toList())
                .build();
    }


    // 2. 비디오 삭제
    @Transactional
    public void destroyRoom(User user, VideoRoomDestroyRequest request) throws Exception {


        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        Room room = roomMemoryRepository.findById(response.getRoom());


        roomMemoryRepository.delete(room.getId());
    }

    public Room findByRoomId(Long roomId) {
        return roomMemoryRepository.findById(roomId);
    }
}
