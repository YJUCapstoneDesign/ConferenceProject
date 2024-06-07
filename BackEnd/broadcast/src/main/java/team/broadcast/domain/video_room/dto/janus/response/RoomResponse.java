package team.broadcast.domain.video_room.dto.janus.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomResponse {
    private Long roomId;
    private String username;
}
