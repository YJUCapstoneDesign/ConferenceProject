package team.broadcast.domain.video_room.dto.janus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.janus.exception.JanusErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomResponse {
    private String janus;
    private String transaction;
    private VideoRoomResult response;
    private JanusErrorCode error;

    public boolean isError() {
        return janus.equals("error");
    }

    public boolean isPluginError() {
        return response.getError_code() != null;
    }
}
