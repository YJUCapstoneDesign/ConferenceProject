package jpapractice.jpapractice.dto.board;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

  private Long postId;
  private String postSubject;
  private String postContent;
  private LocalDateTime postDate;
  private Long view;
  private String studentName;
  private String accountId;
  private List<CommentDto> comments;

  @Override
  public String toString() {
    return "PostDto{" +
        "postId=" + postId +
        ", postSubject='" + postSubject + '\'' +
        ", postContent='" + postContent + '\'' +
        ", postDate=" + postDate +
        ", view=" + view +
        ", studentName='" + studentName + '\'' +
        ", accountId='" + accountId + '\'' +
        ", comments=" + comments +
        '}';
  }

}
