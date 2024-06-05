package team.broadcast.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.broadcast.domain.enumstore.enums.UserRole;
import team.broadcast.domain.team.entity.Team;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID_SEQ")
    private Long id;

    @Column(name = "USER_NAME", length = 20)
    private String name;

    @Column(name = "USER_PWD", length = 100)
    private String pwd;


    @Column(name = "USER_IMAGE")
    private String imageUrl;

    @Column(name = "USER_PHONE")
    private String phone;

    @Column(name = "USER_EMAIL", unique = true, length = 50)
    private String email;

    @Column(name = "USER_ADMIN")
    @Enumerated(EnumType.STRING)
    private UserRole admin;

    @Column(name = "USER_PLATFORM", length = 10)
    private String platform;

    @Column(name = "USER_TOKEN")
    private String token;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_TEAM",
            joinColumns = @JoinColumn(name = "USER_ID_SEQ"),
            inverseJoinColumns = @JoinColumn(name = "TM_ID"))
    private List<Team> teams = new ArrayList<>();

    public User(String username) {
        this.name = username;
    }

    // 비밀번호를 제외한 정보 업데이트
    public void changeUserInfo(String username, String phone) {
        this.name = username;
        this.phone = phone;
    }

    public void updatePassword(String password) {
        this.pwd = password;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.token = updateRefreshToken;
    }

    public void deleteRefreshToken() {
        this.token = null;
    }

    public void updateImageUrl(String updateImageUrl) {
        this.imageUrl = updateImageUrl;
    }

    public void updateTeams(List<Team> teams) {
        this.teams = teams;
    }
}
