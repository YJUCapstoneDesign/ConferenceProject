package jpapractice.jpapractice.dto.board;

import java.time.LocalDateTime;

import jpapractice.jpapractice.domain.Post;
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
    private String studentName;

    public static PostListDto fromPost(Post post) {
        PostListDto dto = new PostListDto();
        dto.setId(post.getId());
        dto.setPostSubject(post.getPostSubject());
        dto.setCommentCount((long) post.getComments().size());
        dto.setPostDate(post.getPostDate());
        dto.setStudentName(post.getStudent().getName());

        return dto;
    }

    @Override
    public String toString() {
        return "PostListDto [id=" + id + ", postSubject=" + postSubject
                + ", commentCount=" + commentCount
                + ", postDate=" + postDate + "]";
    }

}
