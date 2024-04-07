package team.broadcast.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUser {
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    private String password;
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phone;
}
