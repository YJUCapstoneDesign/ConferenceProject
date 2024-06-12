package jpapractice.jpapractice.dto.board;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostListDto {

  private Long id;
  private String postSubject;
  private Long commentCount;
  private LocalDateTime postDate;
  private String name;

  @Override
  public String toString() {
    return "PostListDto [id=" + id + ", postSubject=" + postSubject
        + ", commentCount=" + commentCount
        + ", postDate=" + postDate + ", name=" + name + "]";
  }

}
