package team.broadcast.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.broadcast.domain.attender.entity.Attender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/*
회의 방 룸 Entity이다.
 */
@Entity
@Table(name = "TB_MEETING")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MGT_ID")
    private Long id;

    @Column(name = "MGT_NAME", length = 20)
    private String name;

    @Column(name = "MTG_SECRET")
    private String secret; // 화상회의 방 비밀번호(exist? 고민을 해야 한다.)

    @Column(name = "MGT_BIRTH")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "MGT_END")
    private LocalDateTime endTime;

    @OneToMany
    @JoinColumn(name = "MTG_ID")
    private List<Attender> attenders = new ArrayList<>();
}
