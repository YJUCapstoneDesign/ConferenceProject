package team.broadcast.global.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketRoom {
    private Long id;
    private Type type;
    private Map<String, Object> data;

    @JsonIgnore
    private final Set<WebSocketSession> sessions = new HashSet<>();

    public boolean isEnter() {
        return type == Type.ENTER;
    }

    public enum Type {
        ENTER, MSG
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public boolean  isEmptySession() {
        return sessions.isEmpty();
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }
}
