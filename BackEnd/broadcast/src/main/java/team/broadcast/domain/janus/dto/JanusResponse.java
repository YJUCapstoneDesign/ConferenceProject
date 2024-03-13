package team.broadcast.domain.janus.dto;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.broadcast.domain.janus.exception.JanusError;

// 아래 코드를 지네릭 클래스로 만든다. 메시지가 들어가는 부분은 지네릭스로 설정한다.
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

    public boolean isPluginResponse() {
        return payload.has("plugindata");
    }

    public JsonObject getPluginData() {
        return payload.getAsJsonObject("plugindata");
    }

    public boolean isPluginError() {
        return isPluginResponse() && payload.getAsJsonObject("plugindata").getAsJsonObject("data").has("error");
    }

    public JanusError getPluginError() {
        var pluginData = getPluginData().getAsJsonObject("data");
        return new JanusError(pluginData.get("error_code").getAsInt(), pluginData.get("error").getAsString());
    }


    // Session id 와 handle id 반환한다.
    public Long getDataId() {
        return Long.valueOf(getPayload().get("data").getAsJsonObject().get("id").toString());
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
