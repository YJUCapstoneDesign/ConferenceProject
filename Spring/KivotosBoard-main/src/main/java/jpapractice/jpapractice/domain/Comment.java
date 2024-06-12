package jpapractice.jpapractice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "comment")
@Getter
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private long id;

  @Column(name = "comment_comment")
  private String commentText;

  @Column(name = "comment_date")
  private LocalDateTime commentDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  public Comment() {
  }

  @Builder
  public Comment(long id, String commentText, LocalDateTime commentDate,
      Account account,
      Student student, Post post) {
    this.id = id;
    this.commentText = commentText;
    this.commentDate = commentDate;
    this.account = account;
    this.student = student;
    this.post = post;
  }

  public void changeCommentText(String commentText) {
    this.commentText = commentText;
  }

}
