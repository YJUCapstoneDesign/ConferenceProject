package team.broadcast.domain.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;

}
