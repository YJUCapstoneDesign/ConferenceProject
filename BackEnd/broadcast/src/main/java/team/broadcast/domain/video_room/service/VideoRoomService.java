package team.broadcast.domain.video_room.service;


import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.dto.JanusSession;
import team.broadcast.domain.janus.exception.JanusError;
import team.broadcast.domain.janus.repository.JanusSessionRepository;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.VideoRoomDTO;
import team.broadcast.domain.video_room.dto.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.VideoRoomEditRequest;
import team.broadcast.domain.video_room.repository.VideoRoomRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRoomService {

    private final JanusClient janusClient;
    private final WebClient webClient;
    private final Gson gson;
    private final JanusSessionRepository sessionRepository;
    private final VideoRoomRepository videoRoomRepository;

    // 세션 에러 메시지
    private static final String SESSION_NOT_FOUND_EXCEPTION = "사용자의 세션을 찾을 수 없습니다.";


    // 1. 비디오 생성
    @Transactional
    public VideoRoomDTO createRoom(Long userId, VideoRoomCreateRequest payload) throws Exception {
        JanusSession session = sessionRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(SESSION_NOT_FOUND_EXCEPTION));

        JanusResponse response = janusClient.sendMessage(session.getSessionId(), session.getHandleId(), payload)
                .block();
        log.info("create response={}", response);

        if (response != null && (response.isPluginError() || response.isError())) {
            throw new JanusError(response.getError().getCode(), response.getError().getReason());
        }

        VideoRoomDTO room = VideoRoomDTO.builder()
                .roomId(payload.getRoom())
                .roomPwd(payload.getSecret())
                .publisherId(userId)
                .build();

        videoRoomRepository.save(room);
        return room;
    }

    // 2. 비디오 수정
    public VideoRoomDTO updateRoom(VideoRoomEditRequest payload) throws Exception {

        VideoRoomDTO room = videoRoomRepository.findById(payload.getRoom());

        JanusSession session = sessionRepository.findByUserId(room.getPublisherId())
                .orElseThrow(() -> new IllegalArgumentException(SESSION_NOT_FOUND_EXCEPTION));

        JanusResponse response = janusClient.sendMessage(session.getSessionId(), session.getHandleId(), payload)
                .block();
        log.info("create response={}", response);

        if (response != null && (response.isPluginError() || response.isError())) {
            throw new IllegalAccessException("error");
        }

        VideoRoomDTO updateRoom = VideoRoomDTO.builder()
                .roomId(payload.getRoom())
                .roomName(payload.getNewDescription())
                .roomPwd(payload.getNewSecret())
                .build();

        videoRoomRepository.update(updateRoom);
        return updateRoom;
    }

    // 3. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest payload) throws Exception {
        VideoRoomDTO room = videoRoomRepository.findById(payload.getRoom());

        JanusSession session = sessionRepository.findByUserId(room.getPublisherId())
                .orElseThrow(() -> new IllegalArgumentException(SESSION_NOT_FOUND_EXCEPTION));

        // 방을 찾은 다음에 할 수 있도록 코드를 구성해야 한다.
        JanusResponse response = janusClient.sendMessage(session.getSessionId(), session.getHandleId(), payload)
                .block();
        log.info("destroy response={}", response);

        // 4. 에러 처리
        if (response != null && (response.isPluginError() || response.isError())) {
            throw new IllegalAccessException("error");
        }

        videoRoomRepository.delete(payload.getRoom());
    }
}
