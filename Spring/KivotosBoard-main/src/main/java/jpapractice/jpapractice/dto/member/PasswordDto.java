package jpapractice.jpapractice.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {

  @NotEmpty(message = "비밀번호를 입력해주세요")
  private String password;

  @NotEmpty(message = "비밀번호를 다시 입력해주세요")
  private String passwordCheck;
}
