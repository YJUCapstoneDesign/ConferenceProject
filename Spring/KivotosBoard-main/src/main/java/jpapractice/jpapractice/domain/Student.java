package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "student_default_information")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private Long id;

  @Column(name = "student_name")
  private String name;

  @Column(name = "student_age")
  private int age;

  @Column(name = "student_email")
  private String email;

  public void changeAge(int age) {
    this.age = age;
  }

  public void changeEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + ", age=" + age
        + ", email=" + email + "]";
  }

  public Student() {
  }

  @Builder
  public Student(Long id, String name, int age, String email, int type) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
  }

  // @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
  // private List<Post> posts = new ArrayList<>();

  // @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
  // private Account account;

  // @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
  // private Momotalk momotalkAccount;

  // @Override
  // public String toString() {
  // return "Student [id=" + id + ", name=" + name + ", age=" + age + ", email=" +
  // email + ", school=" + school
  // + ", club=" + club + ", position=" + position + ", type=" + type + ", posts="
  // + posts + ", account="
  // + account + ", momotalkAccount=" + momotalkAccount + "]";
  // }
  // toString 메서드를 오버라이딩 해서 필드 전체를 리턴시키면 객체타입 필드들에 의해서 select 문 연산이 시작된다.


}
