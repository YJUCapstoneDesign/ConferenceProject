package team.broadcast.domain.video_room.dto.request;

import lombok.*;

/**
 * Video destroy Request DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomDestroyRequest {
    private Long room;
    private String secret;
    private String request = "destroy";

}
