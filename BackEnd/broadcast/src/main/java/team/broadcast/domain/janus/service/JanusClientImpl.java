package team.broadcast.domain.janus.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class JanusClientImpl implements JanusClient {

    private final WebClient webClient;
    private String endPoint;
    private final Gson gson;

    @Override
    public Mono<JanusResponse> send(JanusRequest request) {
        String json = request.toString();

        log.info("request={}", json);

        endPoint = null;
        // 엔드 포인트 설정
        if (request.getType().equals("attach") || request.getType().equals("destroy")) {
            endPoint = "/" + request.getSessionId();
        } else if (request.getType().equals("message")) {
            endPoint = "/" + request.getSessionId() + "/" + request.getHandleId();
        }

        try {
            return webClient.post()
                    .uri(endPoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(json)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(resp ->
                            new JanusResponse(JsonParser.parseString(resp)
                                    .getAsJsonObject()));
        } catch (Exception e) {
            log.error("Not response Error={}", e.getMessage());
            return null;
        }

    }


    @Override
    public Mono<JanusResponse> createSession() {
        JanusRequest request = new JanusRequest("create", JanusRequest.newTransaction(), null, null);

        return send(request);
    }

    @Override
    public Mono<JanusResponse> sendMessage(Long sessionId, Long handleId, Object payload) {
        JanusRequest request = new JanusRequest("message", JanusRequest.newTransaction(), sessionId, handleId, new JsonObject());
        request.getPayload().add("body", gson.toJsonTree(payload));
        return send(request);
    }

    // alive 생략
}
