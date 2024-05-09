package team.broadcast.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdate {
    @NotBlank
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "비밀번호는 8자 이상이며, 숫자와 특수문자를 모두 포함해야 합니다.")
    private String newPassword;
}
