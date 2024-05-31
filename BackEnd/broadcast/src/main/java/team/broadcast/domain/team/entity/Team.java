package team.broadcast.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import team.broadcast.domain.enumstore.enums.TeamRole;
import team.broadcast.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_TEAM")
public class Team {
    @Id
    @Column(name = "TM_ID")
    private Long id;

    @Column(name = "TM_PASSWORD", length = 100)
    private String password;

    @Column(name = "TM_CREATION")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "TM_ROLE")
    @Enumerated(EnumType.STRING)
    private TeamRole role;

    @ManyToMany(mappedBy = "teams")
    private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user) {
        userList.remove(user);
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
