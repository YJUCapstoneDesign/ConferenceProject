package team.broadcast.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import team.broadcast.global.exception.handler.dto.WebsocketData;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class MindMapHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;

    private final Set<WebSocketSession> users = new HashSet<>();
    private final Map<Long, Set<WebSocketSession>> sessionMap = new HashMap<>();

    // websocket 접속시 사용되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("{} 님이 접속하셨습니다.", session.getId());
        users.add(session);
    }

    // websocket 종료시 사용되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("{} 님이 퇴장하셨습니다.", session.getId());
        users.remove(session);
    }

    // websocket 연결 중 메시지 전달시 사용되는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.info("received text message: {}", payload);

        WebsocketData websocketData = objectMapper.readValue(payload, WebsocketData.class);

        Long roomId = websocketData.getId();

        if (!sessionMap.containsKey(roomId)) {
            sessionMap.put(roomId, new HashSet<>());
        }

        Set<WebSocketSession> room = sessionMap.get(roomId);

        room.add(session);

        // 해당 방안에 있는 사용자들에게 데이터를 보낸다.
        for (WebSocketSession ses : room) {
            ses.sendMessage(new TextMessage(payload));
        }
    }
}
