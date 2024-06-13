package jpapractice.jpapractice.repository;

import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByStudent(Student student);
}
