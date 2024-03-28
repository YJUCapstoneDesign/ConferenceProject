package team.broadcast.domain.janus.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.dto.JanusRequest;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JanusClientImpl implements JanusClient {

    private final WebClient webClient;
    @Value("${janus.admin.secret}")
    private String adminSecret;
    private final Gson gson;

    @Override
    public <T> Mono<T> send(Object request, Class<T> classOf) {
        JanusRequest requestBody = JanusRequest.create(UUID.randomUUID().toString(), adminSecret, request);
        String json = gson.toJson(requestBody);

        log.info("Request json={}", json);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> gson.fromJson(resp, classOf)); // json 문자열을 객체로 변환한다.

    }
}
