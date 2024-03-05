package team.broadcast.domain.janus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JanusRequestDTO {
    private String janus;
    private String plugin;
    private String transaction;
    private Object body;

    /**
     * janus - janus 요청 구분
     * plugin - 어떤 plugin 인지에 대한 요청 구분
     * transaction - 요청에 대한 구분을 위한 랜덤한 것
     * request - 요청을 위한 payload
     */
    public static JanusRequestDTO create(String transaction, Object body) {
        return new JanusRequestDTO("attach", "janus.plugin.videoroom", transaction, body);
    }

    public static JanusRequestDTO createSession() {
        return new JanusRequestDTO("create", null, UUID.randomUUID().toString(), null);
    }
}
