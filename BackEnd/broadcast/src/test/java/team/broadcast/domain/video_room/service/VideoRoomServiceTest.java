package team.broadcast.domain.video_room.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.dto.JanusSession;
import team.broadcast.domain.janus.enums.JanusPlugin;
import team.broadcast.domain.janus.handle.VideoRoomHandle;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoRoomServiceTest {

    @Autowired
    private VideoRoomService videoRoomService;
    @Autowired
    private JanusClient client;
    private VideoRoomHandle handle;


    // 초기화 작업
    @BeforeEach
    public void init() throws Exception {
        JanusSession session = JanusSession.builder()
                .id(1L)
                .UserId(1L)
                .build();
        JanusResponse sessionResponse = client.createSession().block();
        session.setSessionId(sessionResponse.getDataId());
        JsonObject json = new JsonObject();
        json.add("plugin", JsonParser.parseString(JanusPlugin.VIDEO_ROOM.getPlugin()));

        JanusRequest request = new JanusRequest("attach", JanusRequest.newTransaction(), session.getSessionId(), null, json);
        JanusResponse response = client.send(request).block();
        handle = new VideoRoomHandle(session, response.getDataId(), client, new Gson());
        videoRoomService.setHandle(handle);
    }

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        Long roomId = 4000L;
        String secret = "1234";
        // 방 생성
        VideoRoomCreateRequest createRequest = new VideoRoomCreateRequest(roomId, secret, secret);
        VideoRoomDTO room = videoRoomService.createRoom(1L, createRequest);

        // 방 삭제
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
                .room(roomId)
                .secret(secret)
                .newSecret(newSecret)
                .build();

        VideoRoomDTO updatedRoom = videoRoomService.updateRoom(editRequest);


        // 방 삭제
        VideoRoomDestroyRequest videoRoomDestroyRequest = new VideoRoomDestroyRequest(roomId, newSecret);
        videoRoomService.destroyRoom(videoRoomDestroyRequest);
    }
}