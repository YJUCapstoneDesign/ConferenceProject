package team.broadcast.domain.janus.dto;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Janus 요청에 필요한 Request Dto
 */
@RequiredArgsConstructor
@Setter
@Getter
public class    JanusRequest {
    private final String type; // request 요청 종류를 나타낸다.
    private final String transaction; // 요청을 구별하기 위해 사용한다.
    private final Long sessionId; // 사용자를 나타내는 id
    private final Long handleId; // plugin 사용을 위해서는 handle id가 필요하다.
    private final JsonObject payload; // api 요청시 보낼 정보들이 들어가는 곳이다.

    public JanusRequest(String type, String transaction, Long sessionId) {
        this(type, transaction, sessionId, null, null);
    }

    @Override
    public String toString() {
        JsonObject json = new JsonObject();
        if (payload != null) json = payload.deepCopy();
        json.addProperty("janus", type);
        json.addProperty("transaction", transaction);
        if (sessionId != null) json.addProperty("session_id", sessionId);
        if (handleId != null) json.addProperty("handle_id", handleId);
        return json.toString();
    }
}
