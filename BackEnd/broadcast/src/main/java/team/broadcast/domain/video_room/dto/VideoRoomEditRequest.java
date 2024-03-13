package team.broadcast.domain.video_room.dto;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Video edit Request DTO
 */
@Getter
@Builder
@RequiredArgsConstructor
public class VideoRoomEditRequest {
    private final Long room;
    private final String secret;
    @SerializedName("new_description")
    private final String newDescription;
    @SerializedName("new_secret")
    private final String newSecret;
    @SerializedName("new_pin")
    private final String newPin;
    private final String request="edit";
}
