package team.broadcast.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class RoomHandler extends TextWebSocketHandler {
    /*
    구현 목록

    1. 현재 방안에 들어와 있는 유저 구분할 수 있도록 하기
    2. 방안에 아무도 없을 시 자동으로 삭제할 수 있도록 코드 구현
    3. 방에 입장 코드 구현 (방이 열리지 않으면 자동으로 열리게 해야 한다.)
    4. 방 퇴장 코드 구현을 해야 한다.
     */

    // 웹소켓 연결 되었을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    // 웹 소켓 연결이 종료된 경우
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    // 웹 소켓 연결 중 데이터 통신 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }
}
