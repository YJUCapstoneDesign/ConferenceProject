package team.broadcast.domain.janus.request.videoroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.video_room.VideoRoomDTO;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomCreateRequestDto {
    private String request;
    // room Id
    private Long room;
    // 방이름
    private String description;

    private String secret;

    private String pin;

    private boolean is_private;

    public static VideoRoomCreateRequestDto create(VideoRoomDTO roomDTO) {
        return new VideoRoomCreateRequestDto("create", roomDTO.getRoomId(), roomDTO.getRoomName(),
                roomDTO.getRoomPwd().toString(), roomDTO.getRoomPwd().toString(), true);
    }

}
