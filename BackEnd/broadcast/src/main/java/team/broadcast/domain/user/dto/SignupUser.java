package team.broadcast.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupUser {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "유저 이메일", example = "test@example.com")
    private String email;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "비밀번호는 8자 이상이며, 숫자와 특수문자를 모두 포함해야 합니다.")
    @Schema(description = "유저 비밀번호(8자리 이상, 숫자 특수문자 모두 포함)", example = "Test1234!")
    private String password;
    @NotBlank(message = "유저이름을 입력해주세요.")
    @Schema(description = "유저 이름", example = "테스트")
    private String username;
    @Schema(description = "사용자 휴대폰 번호", example = "01012345678")
    private String phone;
}
