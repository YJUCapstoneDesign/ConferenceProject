package team.broadcast.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.broadcast.domain.user.enums.Membership;
import team.broadcast.domain.user.enums.UserRole;

@Entity
@Table(name = "tb_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id_seq")
    private Long id;

    @Column(name = "user_name", length = 20)
    private String name;

    @Column(name = "user_pwd", length = 100)
    private String pwd;

    @Column(name = "user_nickname", length = 20)
    private String nickname;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_email", unique = true, length = 50)
    private String email;

    @Column(name = "user_admin")
    @Enumerated(EnumType.STRING)
    private UserRole admin;

    @Column(name = "user_membership")
    @Enumerated(EnumType.STRING)
    private Membership membership;

    @Column(name = "user_platform", length = 10)
    private String platform;

    public User(String username) {
        this.name = username;
    }
}
