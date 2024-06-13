package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "clubs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_name")
    private String name;

    @Column(name = "club_description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public void updateStudent(Student student) {
        this.student = student;
    }
}
