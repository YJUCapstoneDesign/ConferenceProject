package team.broadcast.domain.video_room.dto.janus.request;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Video create Request DTO
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VideoRoomCreateRequest {
    private Long room; // 방 번호
    private String display; // 방 이름
    private String secret;
    @Builder.Default
    private Integer publishers = 6; // 방 참여자 6명으로 지정
    @SerializedName("is_private")
    private Boolean isPrivate = true;
    @Builder.Default
    private final String request = "create";
    private String email; // 호스트 구분하기 위한 이메일
}