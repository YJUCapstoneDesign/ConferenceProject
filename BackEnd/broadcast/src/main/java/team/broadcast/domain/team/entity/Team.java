package team.broadcast.domain.team.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.broadcast.domain.enumstore.enums.TeamRole;
import team.broadcast.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "TB_USER")
    private List<User> users;
}
