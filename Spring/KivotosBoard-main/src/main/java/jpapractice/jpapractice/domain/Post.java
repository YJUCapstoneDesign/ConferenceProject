package jpapractice.jpapractice.domain;

import jakarta.persistence.CascadeType;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "post")
@Getter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private long id;

  @Column(name = "post_subject")
  private String postSubject;

  @Column(name = "post_post")
  private String postContent;

  @Column(name = "post_date")
  private LocalDateTime postDate;

  @Column(name = "post_view")
  private long view;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "student_id")
  private Student student;

  @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
  private List<Comment> comments = new ArrayList<>();

  public Post() {
  }

  @Builder
  public Post(long id, String subject, String postContent,
              LocalDateTime postDate, long view, Student student, Account account,
              List<Comment> comments) {
    this.id = id;
    this.postSubject = subject;
    this.postContent = postContent;
    this.postDate = postDate;
    this.view = view;
    this.account = account;
    this.student = student;
    this.comments = comments;
  }

  @Override
  public String toString() {
    return "Post{" +
            "id=" + id +
            ", postSubject='" + postSubject + '\'' +
            ", postContent='" + postContent + '\'' +
            ", postDate=" + postDate +
            ", view=" + view +
            ", account=" + account +
            ", student=" + student +
            ", comments=" + comments +
            '}';
  }

  public void changePostSubject(String subject) {
    this.postSubject = subject;
  }

  public void changePostContent(String content) {
    this.postContent = content;
  }
}
