package jpapractice.jpapractice.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistMember {

  private String id;
  private String passwd;
  private String name;
  private int age;
  private String email;
  private int schoolId;
  private int clubId;
  private int positionId;
  private String type;

  @Override
  public String toString() {
    return "RegistMember [id=" + id + ", passwd=" + passwd + ", name="
        + name + ", age=" + age + ", email=" + email
        + ", schoolId=" + schoolId + ", clubId=" + clubId + ", positionId="
        + positionId + ", type=" + type
        + "]";
  }

}
