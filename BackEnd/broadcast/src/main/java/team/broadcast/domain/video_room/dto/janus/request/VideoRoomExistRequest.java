package team.broadcast.domain.video_room.dto.janus.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoRoomExistRequest {
    private Long room;
    private final String request = "exists";

    public static VideoRoomExistRequest create(Long room) {
        return new VideoRoomExistRequest(room);
    }
}
