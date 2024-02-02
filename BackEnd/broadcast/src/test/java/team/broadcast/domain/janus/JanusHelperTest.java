package team.broadcast.domain.janus;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


class JanusHelperTest {

  private JanusHelper janusHelper;

  private MockWebServer mockWebServer;

  @BeforeEach
  void setUp() throws IOException {
    mockWebServer = new MockWebServer(); // 테스트를 위한 가짜 API 서버
    mockWebServer.start();
    WebClient webClient = WebClient.builder()
        .baseUrl(mockWebServer.url("/web/test").toString()) // 안에 Path는 가짜 서버 주소이다.
        .build();
    janusHelper = new JanusHelper(webClient, new com.google.gson.Gson());
  }

  @AfterEach
  void terminate() throws IOException {
    mockWebServer.shutdown(); // 켜진 mockWebServer 끄기
  }

  @Test
  @DisplayName("서버 접속 테스트")
  void getServer() {
    String result = janusHelper.getServer();
    Assertions.assertThat(result).contains("DefaultRequestBodyUriSpec");
  }
}