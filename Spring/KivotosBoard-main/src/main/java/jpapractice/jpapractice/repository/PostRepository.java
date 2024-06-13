package jpapractice.jpapractice.repository;

import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.PostListDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    Long countPostBy();

    @Query("SELECT new jpapractice.jpapractice.dto.board.PostListDto(p.id, p.postSubject, count(c), p.postDate, s.name) "
            + "FROM Post p "
            + "JOIN p.student s "
            + "LEFT JOIN p.comments c "
            + "GROUP BY p.id, s.id " // Ensure you group by all selected fields that are not aggregated
            + "ORDER BY p.id DESC")
    List<PostListDto> findPosts(Pageable pageable);

    void deleteByStudent(Student student);
}
