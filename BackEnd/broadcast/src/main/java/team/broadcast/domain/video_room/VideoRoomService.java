package team.broadcast.domain.video_room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.broadcast.global.utils.WebsocketClientEndpoint;

/**
 * issue - 공식문서 참고
 * <p>
 * link - https://docs.spring.io/spring-framework/reference/web/webflux-websocket.html#webflux-websocket-client
 * <p>
 * 아래는 임시 코드이므로 수정이 필요하다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoRoomService {
  private final WebsocketClientEndpoint clientEndpoint;

  public void createRoom(String transactionId) {
    // 방 생성 요청 메시지 전송
    String message = "{\"janus\":\"message\",\"body\":{\"request\":\"create\"},\"transaction\":\"" + transactionId + "\"}";
    clientEndpoint.sendMessage(message);
  }

  public void joinRoom(String roomId, String transactionId) {
    // 방 참가 요청 메시지 전송
    String message = "{\"janus\":\"message\",\"body\":{\"request\":\"join\",\"room\":" + roomId + "},\"transaction\":\"" + transactionId + "\"}";
    clientEndpoint.sendMessage(message);
  }

  public void destroyRoom(String roomId, String transactionId) {
    // 방 삭제 요청 메시지 전송
    String message = "{\"janus\":\"message\",\"body\":{\"request\":\"destroy\",\"room\":" + roomId + "},\"transaction\":\"" + transactionId + "\"}";
    clientEndpoint.sendMessage(message);
  }
}
