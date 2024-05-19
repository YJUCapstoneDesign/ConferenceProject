package team.broadcast.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    // websocket 접속시 사용되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("{} 님이 접속하셨습니다.", session.getId());
        users.put(session.getId(), session); // 접속시 세션에 사용자를 추가한다.
    }

    // websocket 종료시 사용되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("{} 님이 퇴장하셨습니다.", session.getId());
        users.remove(session.getId());
    }

    // websocket 연결 중 메시지 전달시 사용되는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();

        Collection<WebSocketSession> ws = users.values();

        ws.forEach(s -> {
            try {
                s.sendMessage(new TextMessage(msg));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
