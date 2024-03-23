package team.broadcast.domain.video_room.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.dto.JanusSession;
import team.broadcast.domain.janus.enums.JanusPlugin;
import team.broadcast.domain.janus.repository.JanusSessionRepository;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.VideoRoomCreateRequest;
import team.broadcast.domain.video_room.dto.VideoRoomDTO;
import team.broadcast.domain.video_room.dto.VideoRoomDestroyRequest;
import team.broadcast.domain.video_room.dto.VideoRoomEditRequest;

@SpringBootTest
class VideoRoomServiceTest {

    @Autowired
    private VideoRoomService videoRoomService;
    @Autowired
    private JanusSessionRepository sessionRepository;
    @Autowired
    private JanusClient client;


    // 초기화 작업
    @BeforeEach
    public void init() throws Exception {
        JanusSession session = new JanusSession();
        session.setUserId(1L);
        JanusResponse sessionResponse = client.createSession().block();
        session.setSessionId(sessionResponse.getDataId());
        JsonObject json = new JsonObject();
        json.add("plugin", JsonParser.parseString(JanusPlugin.VIDEO_ROOM.getPlugin()));

        JanusRequest request = new JanusRequest("attach", JanusRequest.newTransaction(), session.getSessionId(), null, json);
        JanusResponse response = client.send(request).block();
        session.setHandleId(response.getDataId());

        sessionRepository.save(session);
    }

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 5678L;
        String secret = "5678";
        // 방 생성
        VideoRoomCreateRequest createRequest = new VideoRoomCreateRequest(roomId, secret, null);
        VideoRoomDTO room = videoRoomService.createRoom(1L, createRequest);

//        // 방 삭제
        VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest(roomId, secret);
        videoRoomService.destroyRoom(videoRoomDestroyRequest);
    }

    @Test
    @DisplayName("방 수정")
    void editRoom() throws Exception {
        Long roomId = 5555L;
        String secret = "6666";
        String newSecret = "1111";

        // 방 생성
        VideoRoomCreateRequest createRequest = new VideoRoomCreateRequest(roomId, secret, secret);
        VideoRoomDTO room = videoRoomService.createRoom(1L, createRequest);

        // 방 수정
        VideoRoomEditRequest editRequest = VideoRoomEditRequest.builder()
                .room(room.getRoomId())
                .secret(room.getRoomPwd())
                .newSecret(newSecret)
                .build();

        VideoRoomDTO updatedRoom = videoRoomService.updateRoom(editRequest);


        // 방 삭제
        VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest(updatedRoom.getRoomId(), updatedRoom.getRoomPwd());
        videoRoomService.destroyRoom(videoRoomDestroyRequest);
    }
}