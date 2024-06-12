package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Club> clubs = new ArrayList<>();

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
  public Student(Long id, String name, int age, String email) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.email = email;
  }
}
