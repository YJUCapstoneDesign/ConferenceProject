package team.broadcast.domain.video_room.dto.janus.request;

import lombok.*;

/**
 * Video destroy Request DTO
 */
@Getter
@Builder
@AllArgsConstructor
public class VideoRoomDestroyRequest {
    private Long room;
    private String secret;
    @Builder.Default
    private final String request = "destroy";

}
