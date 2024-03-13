package team.broadcast.domain.video_room.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Video create Request DTO
 */
@Getter
@RequiredArgsConstructor
public class VideoRoomCreateRequest {
    private final Long room;
    private final String secret;
    private final String pin;
    private final String request = "create";

    @Override
    public String toString() {
        return "AudioBridgeCreateRoomRequest[" +
                "room=" + room + ", " +
                "secret=" + secret + ", " +
                "pin=" + pin + ", " +
                "request=" + request + ']';
    }
}
