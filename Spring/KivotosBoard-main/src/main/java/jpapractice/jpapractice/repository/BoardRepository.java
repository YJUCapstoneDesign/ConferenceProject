package jpapractice.jpapractice.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import jpapractice.jpapractice.domain.Account;
import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.dto.board.PostListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepository {

  private final EntityManager em;
  private final InformationRepository informationRepository;

  @Autowired
  public BoardRepository(EntityManager entityManager,
      InformationRepository informationRepository) {
    this.em = entityManager;
    this.informationRepository = informationRepository;

  }

  public Post savePost(Post post) {
    em.persist(post);
    return post;
  }

  public Comment saveComment(Comment comment) {
    em.persist(comment);
    return comment;
  }

  public List<PostListDto> findPosts(int startPost) {
    int firstResult = startPost * 30;
    return em.createQuery(
                 "SELECT new jpapractice.jpapractice.dto.board.PostListDto(p.id, p.postSubject, count(c), p.postDate, s.name) "
                     + "FROM Post p "
                     + "JOIN p.student s "
                     + "LEFT JOIN p.comments c "
                     + "GROUP BY p.id "
                     + "ORDER BY p.id DESC",
                 PostListDto.class)
             .setFirstResult(firstResult)
             .setMaxResults(30)
             .getResultList();
    // post 엔티티의 student 필드를 통해 name을 가져오는 것이 게시글 수가 많아질 경우 계산이 비효율적일 것이라 예상
    // 따라서 어플리케이션에서 for문을 사용하지 않고 join을 통해서 원하는 컬럼만 가져올 수 있도록 하기 위해 Dto를 집어넣는 방식으로
    // 쿼리 사용
    // select p.post_id, count(c.post_id), post_subject from post as p join comment
    // as c where p.post_id = c.post_id group by p.post_id;
  }

  public Long countAllPost() {
    return em.createQuery("SELECT COUNT(*) FROM Post p", Long.class)
             .getSingleResult();

  }

  public Optional<Post> getPost(Long postId) {
    String query = "select p"
        + " from Post p"
        + " join fetch p.student s"
        + " left join fetch p.comments c"
        + " left join fetch c.student cs"
        + " where p.id = :id";
    return Optional.ofNullable(em.createQuery(query, Post.class)
                                 .setParameter("id", postId)
                                 .getSingleResult());
    // return em.find(Post.class, postId);
  }

  public Comment getComment(Long commentId) {
    return em.find(Comment.class, commentId);
  }

  public void removePost(Post post) {
    em.remove(post);
  }

  public void removeComment(Comment comment) {

    em.remove(comment);
  }

  public void removePostsByAccount(Account account) {
    String query = "DELETE FROM Post p WHERE p.account = :account";
    em.createQuery(query).setParameter("account", account).executeUpdate();
  }

  public void removeCommentsBtAccount(Account account) {
    String query = "DELETE FROM Comment c WHERE c.account = :account";
    em.createQuery(query).setParameter("account", account).executeUpdate();
  }

  // public List<Post> findPosts2(int startPost) {
  // int startPosition = startPost * 30;
  // return em.createQuery(
  // "SELECT p "
  // + "FROM Post p "
  // + "JOIN p.student s "
  // + "ORDER BY p.id DESC",
  // Post.class)
  // .setFirstResult(startPosition)
  // .setMaxResults(30)
  // .getResultList();
  // // post 엔티티의 student 필드를 통해 name을 가져오는 것이 게시글 수가 많아질 경우 계산이 비효율적일 것이라 예상
  // // 따라서 어플리케이션에서 for문을 사용하지 않고 join을 통해서 원하는 컬럼만 가져올 수 있도록 하기 위해 Dto를 집어넣는
  // 방식으로
  // // 쿼리 사용
  // }

  // public List<PostListDto> findPostsDto() {
  // return em.createQuery(
  // "SELECT new jpapractice.jpapractice.dto.board.PostListDto(p.id, p.postSubject,
  // p.postDate, s.name) "
  // + "FROM Post p "
  // + "JOIN p.student s "
  // + "ORDER BY p.id DESC",
  // PostListDto.class)
  // .getResultList();
  // // post 엔티티의 student 필드를 통해 name을 가져오는 것이 게시글 수가 많아질 경우 계산이 비효율적일 것이라 예상
  // // 따라서 어플리케이션에서 for문을 사용하지 않고 join을 통해서 원하는 컬럼만 가져올 수 있도록 하기 위해 Dto를 집어넣는
  // 방식으로
  // // 쿼리 사용
  // }

  // public List<Post> findPostsEntity() {
  // return em.createQuery(
  // "SELECT p "
  // + "FROM Post p "
  // + "JOIN p.student s "
  // + "ORDER BY p.id DESC",
  // Post.class)
  // .getResultList();
  // // post 엔티티의 student 필드를 통해 name을 가져오는 것이 게시글 수가 많아질 경우 계산이 비효율적일 것이라 예상
  // // 따라서 어플리케이션에서 for문을 사용하지 않고 join을 통해서 원하는 컬럼만 가져올 수 있도록 하기 위해 Dto를 집어넣는
  // 방식으로
  // // 쿼리 사용
  // }

  // public List<Post> findPostsFetch() {
  // return em.createQuery(
  // "SELECT p "
  // + "FROM Post p "
  // + "JOIN FETCH p.student s "
  // + "ORDER BY p.id DESC",
  // Post.class)
  // .getResultList();
  // // post 엔티티의 student 필드를 통해 name을 가져오는 것이 게시글 수가 많아질 경우 계산이 비효율적일 것이라 예상
  // // 따라서 어플리케이션에서 for문을 사용하지 않고 join을 통해서 원하는 컬럼만 가져올 수 있도록 하기 위해 Dto를 집어넣는
  // 방식으로
  // // 쿼리 사용
  // }

}
