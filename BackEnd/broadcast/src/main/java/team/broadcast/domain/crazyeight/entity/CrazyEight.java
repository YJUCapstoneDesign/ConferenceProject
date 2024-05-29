package team.broadcast.domain.crazyeight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CRAZY_EIGHT")
public class CrazyEight {
    //TODO 엔티티 정의하기
    @Id
    @Column(name = "TM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
