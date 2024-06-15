package team.broadcast.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class HatHandler extends TextWebSocketHandler {

    // 현재 연결된 유저 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Long, Set<WebSocketSession>> sessionMap = new HashMap<>();

    // 소켓 연결 뒤
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connected to {}", session.getId());
        sessions.add(session);
    }

    // 소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Disconnected from {}", session.getId());
        sessions.remove(session);
    }

    // 소켓 데이터 전송 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 전송한 데이터 부분
        log.info("received message: {}", payload);

        // 채팅 부분 코드를 아래에 작성을 해봅시다.
        sessions.forEach(s -> {
            try {
                // 같은 사람인 경우
                if (s.getId().equals(session.getId())) {
                    s.sendMessage(new TextMessage("[M]" + payload));
                    return;
                }

                s.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
