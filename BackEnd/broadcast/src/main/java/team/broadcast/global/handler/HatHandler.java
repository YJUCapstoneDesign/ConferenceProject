package team.broadcast.global.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class HatHandler extends TextWebSocketHandler {
    private final Gson gson;
    private final Map<Long, Set<WebSocketSession>> rooms = new HashMap<>();

    // 소켓 연결 뒤
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connected to {}", session.getId());
    }

    // 소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Disconnected from {}", session.getId());
        // 세션이 방에 속한 곳 전부 삭제
        rooms.values().forEach(sess -> sess.remove(session));
    }

    // 소켓 데이터 전송 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 전송한 데이터 부분
        log.info("received message: {}", payload);

        Map map = gson.fromJson(payload, Map.class);

        log.info("data: {}", map);

        Double id = (Double) map.get("id");

        Long roomId = id.longValue();
        String data = (String) map.get("data");

        Set<WebSocketSession> webSocketSessions = rooms.computeIfAbsent(roomId, k -> new HashSet<>());

        webSocketSessions.add(session);

        // 메시지 전송하기
        for (WebSocketSession sess : webSocketSessions) {
            if (sess.isOpen()) {
                if (sess.getId().equals(session.getId())) {
                    sess.sendMessage(new TextMessage("[M]" + data));
                } else {
                    sess.sendMessage(new TextMessage(data));
                }
            }
        }
    }
}
