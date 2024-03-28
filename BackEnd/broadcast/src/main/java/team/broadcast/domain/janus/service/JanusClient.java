package team.broadcast.domain.janus.service;

import reactor.core.publisher.Mono;
import team.broadcast.domain.janus.dto.JanusRequest;

public interface JanusClient {
    <T> Mono<T> send(Object request, Class<T> classOf);
}
