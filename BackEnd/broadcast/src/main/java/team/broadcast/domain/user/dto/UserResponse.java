package team.broadcast.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.enumstore.enums.Membership;
import team.broadcast.domain.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Membership membership;

    // 회원인지 확인하는 메서드
    public boolean isMember() {
        return membership == Membership.MEMBER;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .membership(user.getMembership())
                .build();
    }
}
