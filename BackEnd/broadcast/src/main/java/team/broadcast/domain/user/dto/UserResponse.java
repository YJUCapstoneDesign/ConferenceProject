package team.broadcast.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.broadcast.domain.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String nickname;
    private String imageUrl;
    private String email;
    private String phone;


    public static UserResponse from(User user) {
        return UserResponse.builder()
                .username(user.getName())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
