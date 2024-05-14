package team.broadcast.domain.video_room.dto.janus.request;


import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Video edit Request DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRoomEditRequest {
    private Long room;
    private String secret;
    @SerializedName("new_description")
    private String newDescription;
    @SerializedName("new_secret")
    private String newSecret;
    private final String request = "edit";
}
