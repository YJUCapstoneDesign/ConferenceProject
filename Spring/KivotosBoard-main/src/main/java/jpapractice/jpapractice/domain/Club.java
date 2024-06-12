package jpapractice.jpapractice.domain;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "club_info")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_name")
    private String name;

    // @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    // private List<ClubPosition> clubPosition;

    // @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    // private List<Student> student;

    public Club() {
    }

    @Builder
    public Club(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
