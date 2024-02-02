package team.broadcast.domain.chatuser;


import lombok.*;
import team.broadcast.domain.enums.UserRole;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatUserDTO {
  private Long userId; // 사용자를 구분하기 위한 Id
  private String username; // 사용자 이름
  private String nickname; // 사용자 닉네임 (화면에 보여줄 이름)
  private UserRole userRole; // 호스트와 참석자 구분
  private boolean membership; // 멤버쉽 구분 (0과 1로 구분된다.)
}
