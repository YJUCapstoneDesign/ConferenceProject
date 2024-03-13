package team.broadcast.domain.attender.entity;


import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "TB_ATTENDER")
public class Attender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDER_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MGT_ID")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "USER_ID_SEQ")
    private User user;

}
