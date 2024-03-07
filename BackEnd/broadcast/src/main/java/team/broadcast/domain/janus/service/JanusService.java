package team.broadcast.domain.janus.service;

import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;

public interface JanusService {
    JanusResponse send(JanusRequest request) throws Exception;

    JanusResponse createSession() throws Exception;
}
