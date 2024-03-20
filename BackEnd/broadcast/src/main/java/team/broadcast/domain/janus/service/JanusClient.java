package team.broadcast.domain.janus.service;

import com.google.gson.JsonObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;
import team.broadcast.domain.janus.enums.JanusPlugin;

public interface JanusClient {
    Mono<JanusResponse> send(JanusRequest request);

    Mono<JanusResponse> createSession();

    Mono<JanusResponse> sendMessage(Long sessionId, Long handleId, Object payload);
}
