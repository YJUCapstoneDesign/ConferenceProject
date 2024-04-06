package team.broadcast.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.enumstore.enums.Membership;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private Membership membership;

    // 회원인지 확인하는 메서드
    public boolean isMember() {
        return membership == Membership.MEMBER;
    }

}
