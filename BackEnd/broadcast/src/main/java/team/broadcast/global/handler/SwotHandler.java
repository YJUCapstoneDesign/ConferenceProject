package team.broadcast.global.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import team.broadcast.global.handler.dto.WebsocketRoom;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SwotHandler extends TextWebSocketHandler {
    private final Gson gson;

    private final Set<WebSocketSession> users = new HashSet<>();
    private final Map<Long, WebsocketRoom> rooms = new HashMap<>();

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
            WebsocketRoom resRoom = rooms.get(roomId);
            if (resRoom == null) {
                resRoom = new WebsocketRoom(roomId, null, null);
            }
            resRoom.addSession(session);
            rooms.put(roomId, resRoom);
            String sendData = resRoom.getData() == null ? generateInitialJson() : toJson(resRoom.getData());
            log.info("sendData: {}", sendData);
            session.sendMessage(new TextMessage(sendData));
            return;
        }

        log.info("received room data: {}", toJson(websocketRoom.getData()));

        // 이미 들어와 있는 경우 최신 데이터를 업데이트하고 보낼 수 있도록 한다.
        updateRoomById(roomId, websocketRoom);

        log.info("rooms session : {}", rooms.get(roomId).getSessions().stream().toList());

        // 최신 데이터를 현재 접속되어 있는 사용자에게 모두 뿌려준다.
        sendMessage(roomId, users);
    }

    private void sendMessage(Long roomId, Set<WebSocketSession> sessions) throws IOException {
        for (WebSocketSession session : sessions) {
            // 방아이디가 같은 사람만 보낸다.
            if (rooms.get(roomId).getSessions().contains(session)) {
                session.sendMessage(new TextMessage(toJson(rooms.get(roomId).getData())));
            }
        }
    }

    private void updateRoomById(Long roomId, WebsocketRoom websocketRoom) {
        WebsocketRoom findRoom = rooms.get(roomId);
        findRoom.setData(websocketRoom.getData());
        rooms.put(roomId, findRoom);
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

    // 아래 코드 수정 가능하도록 한다.
    private String generateInitialJson() {
        final int SIZE = 4;

        Map<String, Object> data = new HashMap<>();

        List<List<Map<String, Object>>> dataList = new ArrayList<>();

        for (int i = 0; i < SIZE * 2; i++) {
            List<Map<String, Object>> innerList = new ArrayList<>();
            for (int j = 0; j < SIZE * 2; j++) {
                Map<String, Object> tile = new HashMap<>();
                tile.put("title", "");
                tile.put("content", "");
                tile.put("area", 0);
                innerList.add(tile);
            }
            dataList.add(innerList);
        }

        data.put("tiles", dataList);

        return gson.toJson(data);
    }
}
