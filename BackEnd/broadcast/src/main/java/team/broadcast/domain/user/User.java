package team.broadcast.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.broadcast.domain.enums.ChatUserRole;
import team.broadcast.domain.enums.Membership;
import team.broadcast.domain.enums.UserRole;

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
    private Long seq;

    @Column(name = "user_id")
    private String id;

    @Column(name = "user_pwd", length = 100)
    private String pwd;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_email", length = 50)
    private String email;

    @Column(name = "user_admin")
    @Enumerated(EnumType.STRING)
    private UserRole admin;

    @Column(name = "user_membership")
    @Enumerated(EnumType.STRING)
    private Membership membership;

    @Column(name = "user_platform")
    private String platform;

    public User(String username) {
        this.name = username;
    }
}
