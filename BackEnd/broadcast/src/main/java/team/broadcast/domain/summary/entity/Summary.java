package team.broadcast.domain.summary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import team.broadcast.domain.meeting.entity.Meeting;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_SUMMARY")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SM_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "MTG_ID")
    private Meeting meeting;

    @Column(name = "SM_DATE")
    @CreatedDate
    private LocalDateTime localDateTime;

    @Column(name = "SM_DATA")
    private String data; // 요약 정보가 들어가는 곳
}
