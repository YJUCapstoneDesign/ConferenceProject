package team.broadcast.domain.video_room.dto.janus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomResult {
    private Long room;
    private String videoroom;
    private Boolean exists;
    private Integer error_code;
    private String error;

    public boolean isExists() {
        return exists;
    }
}
