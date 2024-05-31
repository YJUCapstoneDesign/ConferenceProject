package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.janus.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.janus.response.VideoRoomResult;
import team.broadcast.domain.video_room.entity.Room;
import team.broadcast.domain.video_room.repository.RoomMemoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final JanusClient janusClient;
    private final RoomMemoryRepository roomMemoryRepository;

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
    public Long createRoom(VideoRoomCreateRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);
        VideoRoomResult response = block.getResponse();

        Room room = Room.builder()
                .id(response.getRoom())
                .name(request.getDisplay())
                .build();

        roomMemoryRepository.save(room);

        return room.getId();
    }

    // 2. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = checkExceptionResponse(send);

        VideoRoomResult response = block.getResponse();

        Room room = roomMemoryRepository.findById(response.getRoom());


        roomMemoryRepository.delete(room.getId());
    }

    // 참석자 추가
    @Transactional
    public void addUser(Long roomId, String username) {
        Room room = roomMemoryRepository.findById(roomId);

        log.info("Adding user {} to room {}", username, roomId);

        room.addUser(username);

        room.setCurrentCount(room.getCurrentCount() + 1);

        roomMemoryRepository.save(room);
    }

    // 참석자 제거
    @Transactional
    public void removeUser(Long roomId, String username) {
        Room room = roomMemoryRepository.findById(roomId);

        room.removeUser(username);

        try {
            // 방안에 인원이 없으면 자동으로 삭제를 하는 코드
            if (room.getCurrentCount() == 0) {
                VideoRoomDestroyRequest destroyRequest = VideoRoomDestroyRequest.builder()
                        .room(room.getId())
                        .build();
                destroyRoom(destroyRequest);
                roomMemoryRepository.delete(roomId);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        room.setCurrentCount(room.getCurrentCount() - 1);

        roomMemoryRepository.save(room);
    }

    // 현재 방안에 있는 참석자 수 가져오기
    public int getRoomUserCount(Long roomId) {
        Room room = roomMemoryRepository.findById(roomId);
        return room.getCurrentCount();
    }
}
