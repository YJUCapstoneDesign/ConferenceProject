package team.broadcast.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.broadcast.domain.attender.entity.Attender;
import team.broadcast.domain.enumstore.enums.Membership;
import team.broadcast.domain.enumstore.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID_SEQ")
    private Long id;

    @Column(name = "USER_NAME", length = 20)
    private String name;

    @Column(name = "USER_PWD", length = 100)
    private String pwd;

    @Column(name = "USER_NICKNAME", length = 20)
    private String nickname;

    @Column(name = "USER_PHONE")
    private String phone;

    @Column(name = "USER_EMAIL", unique = true, length = 50)
    private String email;

    @Column(name = "USER_ADMIN")
    @Enumerated(EnumType.STRING)
    private UserRole admin;

    @Column(name = "USER_MEMBERSHIP")
    @Enumerated(EnumType.STRING)
    private Membership membership;

    @Column(name = "USER_PLATFORM", length = 10)
    private String platform;

    @OneToMany
    @JoinColumn(name = "USER_ID_SEQ")
    private List<Attender> attenders = new ArrayList<>(); // 양방향 연결

    public User(String username) {
        this.name = username;
    }

    public void changeUserInfo(String username, String nickname, String password, String phone) {
        this.name = username;
        this.pwd = password;
        this.nickname = nickname;
        this.phone = phone;
    }
}
