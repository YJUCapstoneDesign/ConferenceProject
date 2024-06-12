package jpapractice.jpapractice.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAndAccountDto {


  @NotEmpty(message = "이름은 필수 항목 입니다.")
  private String name;

  @NotNull(message = "나이는 필수 항목 입니다.")
  private int age;

  @NotEmpty(message = "이메일은 필수 항목 입니다.")
  @Email
  private String email;

  @NotEmpty(message = "아이디는 필수 항목 입니다.")
  @Size(min = 8, max = 25)
  private String accountId;

  @NotEmpty(message = "비밀번호는 필수 항목 입니다.")
  private String accountPasswd;

  @NotEmpty(message = "비밀번호 재입력은 필수 항목 입니다.")
  private String accountPasswdCheck;

  @Override
  public String toString() {
    return "StudentAndAccountDto{" +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", email='" + email + '\'' +
            ", accountId='" + accountId + '\'' +
            ", accountPasswd='" + accountPasswd + '\'' +
            ", accountPasswdCheck='" + accountPasswdCheck + '\'' +
            '}';
  }
}
