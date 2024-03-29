package team.broadcast.domain.video_room.dto.request;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Video create Request DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoRoomCreate {
    private Long room;
    private String display;
    private String secret; // 수정 삭제시 필요한 비밀번호
    private String pin; // 방 입장 비밀 번호
    private Integer publishers = 6; // 방 참여자 6명으로 지정
    @SerializedName("is_private")
    private Boolean isPrivate = true;
    private String request = "create";
    private String email;
}