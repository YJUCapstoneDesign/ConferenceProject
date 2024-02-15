package team.broadcast.domain.video_room;

import org.junit.jupiter.api.Test;
import team.broadcast.global.utils.WebsocketClientEndpoint;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class VideoRoomServiceTest {

  @Test
  void createRoom() throws URISyntaxException {
    // give

    URI serverURI = new URI("ws://110.35.222.59:8088/janus");
    WebsocketClientEndpoint clientEndpoint = new WebsocketClientEndpoint(serverURI);

    VideoRoomService videoRoomService = new VideoRoomService(clientEndpoint);
    // when
    videoRoomService.createRoom("a123456");

    // then
  }
}