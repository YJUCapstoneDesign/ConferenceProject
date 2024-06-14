package jpapractice.jpapractice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


    @ManyToMany(mappedBy = "clubs")
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
        student.getClubs().add(this); // 양방향 연관관계 설정
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getClubs().remove(this);
    }
}
