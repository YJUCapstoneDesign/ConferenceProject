package team.broadcast.domain.video_room.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.broadcast.domain.video_room.enums.VideoRoomJoinType;

/**
 * Video join(publisher) Request DTO
 */
@RequiredArgsConstructor
@Getter
public class VideoRoomJoinPublisherRequest {
    private final String request = "join";

    @SerializedName("ptype")
    private final VideoRoomJoinType TYPE = VideoRoomJoinType.PUBLISHER;

    private final Long room;

    private final String display;

    private final String pin;

}
