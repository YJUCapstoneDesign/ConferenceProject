package jpapractice.jpapractice.dto.board;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WritePostDto {

  @NotNull(message = "제목을 입력해주세요")
  private String postSubject;
  @NotNull(message = "내용을 입력해주세요")
  private String postContent;

  private String writer;

}
