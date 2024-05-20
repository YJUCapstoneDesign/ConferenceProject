package team.broadcast.domain.attender.entity;


import jakarta.persistence.*;
import lombok.*;
import team.broadcast.domain.enumstore.enums.MeetingRole;
import team.broadcast.domain.meeting.entity.Meeting;
import team.broadcast.domain.user.entity.User;

/*
회의 참석자 테이블이다.
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "TB_ATTENDER")
public class Attender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MGT_ID", updatable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "USER_ID_SEQ")
    private User user;


    @Column(name = "USER_MTG_ROLE")
    @Enumerated(EnumType.STRING)
    private MeetingRole role;

    public boolean isHost() {
        return this.role == MeetingRole.HOST;
    }

    public void updateRole(MeetingRole role) {
        this.role = role;
    }
}
