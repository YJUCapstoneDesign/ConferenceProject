package team.broadcast.domain.janus.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import team.broadcast.domain.janus.dto.JanusRequest;
import team.broadcast.domain.janus.dto.JanusResponse;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JanusServiceImpl implements JanusService {

    private final WebClient webClient;
    private String endPoint = null;

    @Override
    public JanusResponse send(JanusRequest request) throws Exception {
        String json = request.toString();

        // 엔드 포인트 설정
        if (request.getType().equals("attach") || request.getType().equals("destroy")) {
            endPoint = "/" + request.getSessionId();
        } else if (request.getType().equals("message")) {
            endPoint = "/" + request.getSessionId() + "/" + request.getHandleId();
        }

        JanusResponse response = webClient.post()
                .uri(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class) // 여기 부분을 수정해야 할 필요가 있다.
                .map(resp -> new JanusResponse(JsonParser.parseString(resp).getAsJsonObject())).block();

        if (response == null) {
            throw new RuntimeException("Response Error");
        }

        // 에러 코드인 경우 에러 발생
        if (response.isError()) {
            throw new RuntimeException(response.getError());
        }
        log.info("response={}", response);

        return response;
    }

    @Override
    public JanusResponse createSession() throws Exception {
        JanusRequest request = new JanusRequest("create", UUID.randomUUID().toString(), null);

        return send(request);
    }
}
