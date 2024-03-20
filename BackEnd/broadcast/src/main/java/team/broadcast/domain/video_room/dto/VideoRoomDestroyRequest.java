package team.broadcast.domain.video_room.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Video destroy Request DTO
 */
@Getter
@RequiredArgsConstructor
public class VideoRoomDestroyRequest {
    private final Long room;
    private final String secret;
    private final String request = "destroy";

}
