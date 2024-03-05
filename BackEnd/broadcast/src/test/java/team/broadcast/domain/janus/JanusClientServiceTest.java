package team.broadcast.domain.janus;


import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.request.videoroom.VideoRoomCreateRequestDto;
import team.broadcast.domain.janus.response.SessionResponseDto;
import team.broadcast.domain.janus.response.videoroom.VideoRoomResponseDto;
import team.broadcast.domain.video_room.VideoRoomDTO;

import java.util.UUID;

@SpringBootTest
class JanusClientServiceTest {

    @Autowired
    private JanusClientService janusClientService;

    @Test
    @DisplayName("방 생성요청")
    void creatRoom() throws Exception {
        SessionResponseDto sessionResponse = janusClientService.createSession().block();

        String sessionId = "/" + sessionResponse.getId();

        VideoRoomDTO videoRoomDTO = new VideoRoomDTO(1L, "testRoom", 999);
        VideoRoomCreateRequestDto videoRoomCreateRequest = VideoRoomCreateRequestDto.create(videoRoomDTO);

        SessionResponseDto videoRoomResponse = janusClientService.postAndResponse(sessionId, videoRoomCreateRequest, SessionResponseDto.class).block();

        Assertions.assertThat(videoRoomResponse).isNotNull();
        System.out.println(videoRoomResponse);

        // 방 삭제 코드 작성 필요
    }

}