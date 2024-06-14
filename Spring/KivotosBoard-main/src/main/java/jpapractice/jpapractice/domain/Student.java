package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "student")
@Builder
@ToString
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

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_age")
    private int age;

    @Column(name = "student_email")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "student_club",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    @Builder.Default
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

    public void addClub(Club club) {
        clubs.add(club);
    }
}
