package jpapractice.jpapractice.repository;

import jakarta.persistence.EntityManager;
import jpapractice.jpapractice.domain.Account;
import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InformationRepository {

  private final EntityManager em;

  @Autowired
  public InformationRepository(EntityManager em) {
    this.em = em;
  }

  public Student getStudentReference(int studentId) {
    return em.getReference(Student.class, studentId);

  }

  public Post getPostReference(Long postId) {
    return em.getReference(Post.class, postId);
  }

  public Comment getCommentReference(Long commentId) {
    return em.getReference(Comment.class, commentId);
  }

  public Account getAccountReference(String id) {
    return em.getReference(Account.class, id);
  }

  public Account getAccountById(String id) {
    return em.find(Account.class, id);
  }

  public Account getAccountByStudentInfo(Long id) {
    String query = "SELECT a FROM Account a JOIN a.student s WHERE s.id= :id";
    return em.createQuery(query, Account.class)
             .setParameter("id", id)
             .getSingleResult();
  }
}
