package team.broadcast.domain.janus.dto;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.broadcast.domain.janus.exception.JanusError;

@RequiredArgsConstructor
@Getter
public class JanusResponse {

    private final JsonObject payload;

    public String getType() {
        return payload.get("janus").getAsString();
    }

    public boolean isSuccess() {
        return getType().equals("success");
    }

    public boolean isError() {
        return getType().equals("error");
    }

    public boolean isAck() {
        return getType().equals("ack");
    }


    public JanusError getError() {
        JsonObject error = getPayload().getAsJsonObject("error");
        return new JanusError(error.get("code").getAsInt(), error.get("reason").toString());
    }

    @Override
    public String toString() {
        return payload.toString();
    }
}
