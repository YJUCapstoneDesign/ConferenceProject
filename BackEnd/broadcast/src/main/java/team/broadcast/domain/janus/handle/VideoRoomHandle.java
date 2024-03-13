package team.broadcast.domain.janus.handle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.broadcast.domain.attender.dto.AttenderDTO;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.dto.JanusSession;
import team.broadcast.domain.janus.enums.JanusPlugin;
import team.broadcast.domain.janus.service.JanusClient;
import team.broadcast.domain.video_room.dto.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class VideoRoomHandle implements JanusHandle {
    private List<AttenderDTO> participants; // 참석자들
    private final JanusSession session;
    private final Long handleId; // handleId
    private final JanusClient janusClient;
    private Long roomId;
    private String opaqueId;
    private final Gson gson;


    @Override
    public JanusPlugin getName() {
        return JanusPlugin.VIDEO_ROOM;
    }

    @Override
    public String getOpaqueId() {
        return opaqueId;
    }

    @Override
    public Long getId() {
        return handleId;
    }

    @Transactional
    public boolean create(VideoRoomCreateRequest payload) throws Exception {
        JanusRequest request = new JanusRequest("message", JanusRequest.newTransaction(), session.getSessionId(), handleId, new JsonObject());
        request.getPayload().add("body", gson.toJsonTree(payload));
        JanusResponse response = janusClient.send(request).block();
        log.info("create response={}", response);

        return response != null && response.isSuccess();
    }
    
    public boolean edit(VideoRoomEditRequest payload) throws Exception {
        JanusRequest request = new JanusRequest("message", JanusRequest.newTransaction(), session.getSessionId(), handleId, new JsonObject());
        request.getPayload().add("body", gson.toJsonTree(payload));
        JanusResponse response = janusClient.send(request).block();
        log.info("edit response={}", response);
        return response != null && response.isSuccess();
    }

    @Transactional
    public boolean destroy(VideoRoomDestroyRequest payload) throws Exception {
        JanusRequest request = new JanusRequest("message", JanusRequest.newTransaction(), session.getSessionId(), handleId, new JsonObject());
        request.getPayload().add("body", gson.toJsonTree(payload));
        JanusResponse response = janusClient.send(request).block();
        log.info("destroy response={}", response);

        return response != null && response.isSuccess();
    }

    @Transactional
    public boolean Join(VideoRoomJoinPublisherRequest payload) throws Exception {
        JanusRequest request = new JanusRequest("message", JanusRequest.newTransaction(), session.getSessionId(), handleId, new JsonObject());
        request.getPayload().add("body", gson.toJsonTree(payload));
        // TODO 비동기 처리로 코드 작성할 필요가 있음.
        // 현재 방법을 모르겠음 추가적으로 찾을 필요가 있음.
        janusClient.send(request)
                .subscribe(response -> log.info("join response ={}", response));

        return true; // 임시 코드
    }
}
