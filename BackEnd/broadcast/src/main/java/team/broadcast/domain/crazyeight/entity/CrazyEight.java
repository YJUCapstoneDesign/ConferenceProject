package team.broadcast.domain.crazyeight.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import team.broadcast.domain.team.entity.Team;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CRAZY_EIGHT")
public class CrazyEight {

    @Id
    @ManyToOne
    @JoinColumn(name = "TM_ID")
    private Team team;

    @Column(name = "CE_DATA")
    private String data;

    @Column(name = "CE_CREATE")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "CE_SUMMARY", length = 1000)
    private String summary;

    public void updateData(String data) {
        this.data = data;
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    static class crazyEightId implements Serializable {
        private Long team;
        private Long crazyEight;
    }
}
