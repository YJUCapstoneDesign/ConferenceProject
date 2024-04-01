package team.broadcast.domain.video_room.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.enumstore.enums.Membership;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.domain.video_room.dto.VideoRoom;
import team.broadcast.domain.video_room.dto.request.VideoRoomCreate;
import team.broadcast.domain.video_room.dto.request.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.request.VideoRoomEditRequest;
import team.broadcast.domain.video_room.dto.response.VideoRoomResponse;
import team.broadcast.domain.video_room.dto.response.VideoRoomResult;
import team.broadcast.domain.video_room.repository.VideoRoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRoomService {

    private final JanusClient janusClient;
    private final VideoRoomRepository videoRoomRepository;
    private final UserRepository userRepository;

    private static final int LIMIT_ROOM_SECOND = 40 * 60; // 40분

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

        List<AttenderDTO> arr = new ArrayList<>();
        arr.add(new AttenderDTO(user.getId(), request.getRoom(), MeetingRole.HOST));

        VideoRoomResult response = block.getResponse();

        VideoRoom room = VideoRoom.builder()
                .roomId(response.getRoom())
                .roomName(request.getDisplay())
                .roomPwd(request.getPin())
                .publisherId(user.getId())
                .participants(arr)
                .build();

        videoRoomRepository.save(room);

//        if (user.getMembership() == Membership.BASIC) {
//            scheduleRoomDestruction(request.getRoom(), request.getSecret());
//        }

        return room;
    }

    // 2. 비디오 수정
    @Transactional
    public VideoRoom updateRoom(VideoRoomEditRequest request) throws Exception {

        Mono<VideoRoomResponse> send = janusClient.send(request, VideoRoomResponse.class);

        VideoRoomResponse block = send.block();

        if (block == null) {
            throw new IllegalAccessException("Update Error");
        }
        VideoRoomResult response = block.getResponse();

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

        VideoRoomResult response = block.getResponse();

        VideoRoom room = videoRoomRepository.findById(response.getRoom());

        if (room == null) {
            throw new IllegalAccessException("Destroy Error");
        }

        videoRoomRepository.delete(room.getRoomId());
    }

    @Async
    public void scheduleRoomDestruction(Long roomId, String secret) {
        try {
            TimeUnit.SECONDS.sleep(LIMIT_ROOM_SECOND); // 5초 뒤에 자동 삭제
            log.info("Recording Time Second ={}", LIMIT_ROOM_SECOND);

            VideoRoomDestroyRequest request = new VideoRoomDestroyRequest(roomId, secret, "destroy");
            destroyRoom(request);
        } catch (InterruptedException e) {
            log.error("Error in scheduleRoomDestruction: {}", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Exception in destroying room: {}", e.getMessage());
        }
    }
}
