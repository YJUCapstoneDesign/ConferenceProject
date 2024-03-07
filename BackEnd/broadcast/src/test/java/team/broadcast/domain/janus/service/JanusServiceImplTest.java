package team.broadcast.domain.janus.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.enums.JanusPlugin;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JanusServiceImplTest {

    @Autowired
    private JanusServiceImpl janusService;

    @Test
    @DisplayName("방 생성")
    void createRoom() throws Exception {
        // 세션 생성
        JanusResponse session = janusService.createSession();

        assertThat(session).isNotNull();

        Long sessionId = Long.valueOf(session.getPayload().get("data").getAsJsonObject().get("id").toString());

        JsonObject videoPlugin = new JsonObject();
        videoPlugin.addProperty("plugin", JanusPlugin.VIDEO_ROOM.getPlugin());

        // VideoRoom plugin 사용 -> handle id 받기
        JanusRequest attach = new JanusRequest("attach", UUID.randomUUID().toString(), sessionId, null, videoPlugin);

        JanusResponse attachResponse = janusService.send(attach);

        Long handleId = Long.valueOf(attachResponse.getPayload().get("data").getAsJsonObject().get("id").toString());


        JsonObject body = new JsonObject();
        body.addProperty("request", "create");
        body.addProperty("room", 1234);
        body.addProperty("secret", "1234");


        videoPlugin.add("body", body);

        // 방생성
        JanusRequest create = new JanusRequest("message", UUID.randomUUID().toString(), sessionId, handleId, videoPlugin);

        janusService.send(create);

        videoPlugin.remove("body");
        body = new JsonObject();
        body.addProperty("request", "destroy");
        body.addProperty("room", 1234);
        body.addProperty("secret", "1234");
        videoPlugin.add("body", body);


        // 방 삭제
        JanusRequest destroy = new JanusRequest("message", UUID.randomUUID().toString(), sessionId, handleId, videoPlugin);
        JanusResponse destroyResponse = janusService.send(destroy);

        assertThat(destroyResponse.getType()).isEqualTo("success");
    }
}