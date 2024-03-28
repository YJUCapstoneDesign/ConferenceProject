package team.broadcast.domain.janus.dto;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.UUID;

/**
 * Janus 요청에 필요한 Request Dto
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JanusRequest {
    private String janus;
    private String plugin;
    private String transaction;
    @SerializedName("admin_secret")
    private String adminSecret;
    private Object request; // 여기에 방 생성, 수정, 삭제 dto가 들어 간다.

    public static JanusRequest create(String transaction, String adminSecret, Object request) {
        return new JanusRequest("message_plugin", "janus.plugin.videoroom", transaction, adminSecret, request);
    }
}
