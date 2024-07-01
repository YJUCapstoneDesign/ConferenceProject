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
public class UserDetailResponse {
    private String username;
    private String imageUrl;
    private String email;
    private String phone;


    public static UserDetailResponse from(User user) {
        return UserDetailResponse.builder()
                .username(user.getName())
                .imageUrl(user.getImageUrl())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
