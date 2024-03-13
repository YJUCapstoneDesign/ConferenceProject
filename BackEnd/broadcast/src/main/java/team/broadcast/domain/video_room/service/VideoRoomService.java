package team.broadcast.domain.video_room.service;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.dto.JanusSession;
import team.broadcast.domain.janus.handle.JanusHandle;
import team.broadcast.domain.janus.handle.VideoRoomHandle;
import team.broadcast.domain.janus.repository.JanusSessionMemoryRepository;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoRoomService {

    @Setter
    private VideoRoomHandle handle;

    // 1. 비디오 생성
    @Transactional
    public VideoRoomDTO createRoom(Long userId, VideoRoomCreateRequest request) throws Exception {
        handle.create(request);
        return null; // 임시로 null이 반환되도록 정함.
    }

    // 2. 비디오 수정
    public VideoRoomDTO updateRoom(VideoRoomEditRequest request) throws Exception {
        handle.edit(request);
        return null; // 임시로 한다.
    }

    // 3. 비디오 삭제
    @Transactional
    public void destroyRoom(VideoRoomDestroyRequest request) throws Exception {

        // 방을 찾은 다음에 할 수 있도록 코드를 구성해야 한다.
        handle.destroy(request);
    }

    @Transactional
    public void join(VideoRoomJoinPublisherRequest request) throws Exception {
        handle.Join(request);
    }
}
