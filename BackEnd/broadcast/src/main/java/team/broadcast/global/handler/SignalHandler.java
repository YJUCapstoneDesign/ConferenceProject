package team.broadcast.global.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SignalHandler extends TextWebSocketHandler {

  List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  // 소켓이 연결되었을 때의 이벤트 처리
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // session Management 부분 생각
    // 이 부분은 임시로 정한 것으로 생각할 필요가 있음
    log.info("new Connected!! session id={}", session.getId());
    sessions.add(session);
  }

  // 연결이 끊어졌을 때 이벤트 처리
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("[ws] Session has been closed with status=[{}, {}]", status, session);
  }

  // 소켓 메시지 처리
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//    super.handleTextMessage(session, message);
    // 클라이언트에서 받은 메시지를 처리하는 로직이다.
    // 아래 로직을 작성할 필요가 있음

  }
}
