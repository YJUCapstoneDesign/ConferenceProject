package team.broadcast.domain.janus;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j // 에러 로그 출력
@RequiredArgsConstructor
public class JanusHelper {
  private final WebClient webClient;
  private final Gson gson;
  @Value("${janus.server}")
  private String janusServer; // 서버 주소
  @Value("${janus.admin.secret}")
  private String adminSecret;


  public <T> Mono<T> postAndGetResponseDto(Object requestDto, Class<T> classOfT) {
    String randomId = UUID.randomUUID().toString();
    JanusRequestDTO janusRequest = JanusRequestDTO.create(randomId, adminSecret, requestDto);
    String json = gson.toJson(janusRequest);

    return webClient.post()
        .uri(janusServer)
        .body(BodyInserters.fromValue(json))
        .retrieve()
        .bodyToMono(String.class)
        .map(response -> gson.fromJson(response, classOfT))
        .onErrorMap(Exception::new);
  }

//  // 테스트용 서버 접속 확인
  public String getServer() {
    try {
    String result = webClient.get()
        .uri(janusServer).toString();

    log.info("Success Connect!!");

    return result;
    } catch(Exception e) {
      throw new IllegalArgumentException(); // 연결실패시 에러 발생
    }
  }
}

