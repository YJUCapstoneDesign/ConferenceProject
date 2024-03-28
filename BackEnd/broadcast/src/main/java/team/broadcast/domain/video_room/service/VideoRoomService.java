package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomEditRequest;
import team.broadcast.domain.video_room.dto.response.VideoRoomResponse;
import team.broadcast.domain.video_room.repository.VideoRoomRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRoomService {

    private final JanusClient janusClient;
    private final VideoRoomRepository videoRoomRepository;
    private final UserRepository userRepository;

    // 1. 비디오 생성
    @Transactional
    public VideoRoom createRoom(String email, VideoRoomCreate request) throws Exception {
        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = send.block();
        if (block == null) {
            throw new IllegalAccessException("Response Error");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));

        VideoRoomResponse.VideoRoomResult response = block.getResponse();

        VideoRoom room = VideoRoom.builder()
                .roomId(response.getRoom())
                .roomName(request.getDisplay())
                .roomPwd(request.getPin())
                .publisherId(user.getId())
                .build();

        videoRoomRepository.save(room);

        return room;
    }

    // 2. 비디오 수정
    public VideoRoom updateRoom(VideoRoomEditRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = send.block();

        if (block == null) {
            throw new IllegalAccessException("Update Error");
        }
        VideoRoomResponse.VideoRoomResult response = block.getResponse();

        VideoRoom updateRoom = VideoRoom.builder()
                .roomId(response.getRoom())
                .roomName(request.getNewDescription())
                .roomPwd(request.getNewPin())
                .build();

        videoRoomRepository.update(updateRoom);

        return updateRoom;
    }

    // 3. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest request) throws Exception {


        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = send.block();
        if (block == null) {
            throw new IllegalAccessException("Response Error");
        }

        VideoRoomResponse.VideoRoomResult response = block.getResponse();

        VideoRoom room = videoRoomRepository.findById(response.getRoom());

        if (room == null) {
            throw new IllegalAccessException("Destroy Error");
        }

        videoRoomRepository.delete(room.getRoomId());
    }
}
