package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student_default_information")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "student_account_id", unique = true, nullable = false)
    private String accountId;

    @Column(name = "student_password", nullable = false)
    private String passwd;

    @Column(name = "club")
    private String club;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_age")
    private int age;

    @Column(name = "student_email")
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Club> clubs = new ArrayList<>();

    public void changePasswd(String passwd) {
        this.passwd = passwd;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", passwd='" + passwd + '\'' +
                ", club=" + club +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", clubs=" + clubs +
                '}';
    }
}
