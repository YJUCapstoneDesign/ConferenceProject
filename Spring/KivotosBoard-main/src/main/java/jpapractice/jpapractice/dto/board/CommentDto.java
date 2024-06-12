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
public class CommentDto {

  private Long commentId;
  private String commentText;
  private String accountId;
  private String studentName;
  private LocalDateTime commentDate;

}
