package team.broadcast.domain.video_room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomResponse {
    private String janus;
    private String transaction;
    private VideoRoomResult response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VideoRoomResult {
        private Long room;
        private String videoroom;
    }
}
