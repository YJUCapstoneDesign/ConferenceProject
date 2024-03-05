package team.broadcast.domain.janus;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.response.SessionResponseDto;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class JanusClientService {
    @Value("${janus.server}")
    private String janusURL;

    private final Gson gson;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(janusURL)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<SessionResponseDto> createSession() {
        JanusRequestDTO janusRequest = JanusRequestDTO.createSession();

        String json = gson.toJson(janusRequest);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> gson.fromJson(response, SessionResponseDto.class));
    }

    public <T> Mono<T> postAndResponse(String sessionId, Object requestDto, Class<T> classOfT) {
        JanusRequestDTO janusRequest = JanusRequestDTO.create(UUID.randomUUID().toString(), requestDto);

        String json = gson.toJson(janusRequest);

        log.info("session id ={}", sessionId);
        log.info("janus request ={}", json);
        return webClient.post()
                .uri(sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> gson.fromJson(response, classOfT)); // json 형태의 문자열을 T type 클래스로 변경

    }
}
