package jpapractice.jpapractice.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultInfoDto {

  private String id;
  private String studentName;
  private int age;
  private String email;
  private String schoolName;

  @Override
  public String toString() {
    return "DefaultInfoDto [id=" + id + ", studentName=" + studentName
        + ", age=" + age + ", email=" + email
        + ", schoolName=" + schoolName + "]";
  }

  @Builder
  public DefaultInfoDto(String id, String studentName, int age,
      String email, String schoolName) {
    this.id = id;
    this.studentName = studentName;
    this.age = age;
    this.email = email;
    this.schoolName = schoolName;
  }

}
