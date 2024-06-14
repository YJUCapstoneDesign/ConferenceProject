package jpapractice.jpapractice.repository;

import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByStudent(Student student);
}
