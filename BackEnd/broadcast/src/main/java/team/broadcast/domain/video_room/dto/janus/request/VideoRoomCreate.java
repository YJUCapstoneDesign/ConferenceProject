package team.broadcast.domain.video_room.dto.janus.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Video create Request DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoRoomCreate {
    private Long room; // 방 번호
    private String display; // 방 이름
    private String secret;
    private Integer publishers = 6; // 방 참여자 6명으로 지정
    @SerializedName("is_private")
    private Boolean isPrivate = true;
    private final String request = "create";
    private String email; // 호스트 구분하기 위한 이메일
    private final boolean record = true;
}