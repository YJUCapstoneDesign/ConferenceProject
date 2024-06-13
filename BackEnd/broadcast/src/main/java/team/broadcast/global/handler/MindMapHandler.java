package team.broadcast.global.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import team.broadcast.global.handler.dto.WebsocketRoom;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class MindMapHandler extends TextWebSocketHandler {
    private final Gson gson;

    private final Set<WebSocketSession> users = new HashSet<>();
    private final Map<Long, WebsocketRoom> rooms = new HashMap<>();

    public MindMapHandler(Gson gson) {
        this.gson = gson; // 자동 주입

        Map map = gson.fromJson("""
                {
                "data":{
                "node":[
                {
                "id":"1",
                "position":{
                "x":300,
                "y":300
                },
                "data":{
                "label":"1"
                }
                },
                {
                "id":"2",
                "position":{
                "x":200,
                "y":400
                },
                "data":{
                "label":"2"
                }
                }
                ],
                "edge":[
                {
                "id":"e1-2",
                "source":"1",
                "target":"2"
                }
                ]
                }
                }""", Map.class);
        rooms.put(0L,
                new WebsocketRoom(0L, null, map)
        );
    }

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
        exitRoom(session); // 퇴장 처리
    }

    // websocket 연결 중 메시지 전달시 사용되는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        WebsocketRoom websocketRoom = gson.fromJson(payload, WebsocketRoom.class);

        Long roomId = websocketRoom.getId();

        // 들어온 경우 초기 데이터를 준다.
        if (websocketRoom.isEnter()) {
            session.sendMessage(new TextMessage(toJson(rooms.getOrDefault(roomId, rooms.get(0L)).getData())));
            return;
        }

        log.info("received message: {}", payload);
        log.info("stored room data: {}", toJson(websocketRoom.getData()));
        // 이미 들어와 있는 경우 최신 데이터를 업데이트하고 보낼 수 있도록 한다.
        websocketRoom.addSession(session);
        rooms.put(roomId, websocketRoom);


        // 최신 데이터를 현재 접속되어 있는 사용자에게 모두 뿌려준다.
        sendMessage(roomId, users, payload);
    }

    private void sendMessage(Long roomId, Set<WebSocketSession> sessions, String payload) throws IOException {
        for (WebSocketSession session : sessions) {
            // 방아이디가 같은 사람만 보낸다.
            if (rooms.get(roomId).getSessions().contains(session)) {
                session.sendMessage(new TextMessage(payload));
            }
        }
    }

    private String toJson(Map<String, Object> data) {
        return gson.toJson(data);
    }

    private void exitRoom(WebSocketSession session) {
        rooms.values().stream()
                // 방안에 있는 사용자를 제거한다. (세션은 고유값이다.)
                .filter(room -> room.getSessions().contains(session))
                .findFirst()
                .ifPresent(room -> {
                    room.removeSession(session);
                    // 방안에 아무도 없는 경우 삭제 처리를 한다.
                    if (room.isEmptySession()) {
                        log.info("해당 방이 삭제되었습니다.");
                        rooms.remove(room.getId());
                    }
                });
    }
}
