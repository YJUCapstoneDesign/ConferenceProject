package jpapractice.jpapractice.service;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.PostListDto;
import jpapractice.jpapractice.repository.BoardRepository;
import jpapractice.jpapractice.repository.InformationRepository;
import jpapractice.jpapractice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class BoardServiceTest {

  @Autowired
  BoardService boardService;

  @Autowired
  EntityManager em;

  @Autowired
  BoardRepository boardRepository;

  @Autowired
  InformationRepository informationRepository;

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void GenerateData() {
    String heavy = null;
    for (int i = 0; i < 200; i++) {
      heavy += "1";
    }
    System.out.println(heavy);
    List<Student> 테스트학생 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Student student = new Student()
          .builder()
          .name("name" + i)
          .age(17)
          .email("testemail" + i + "@test.test")
          .build();

      Student saveStudent = memberRepository.saveStudent(student);
      테스트학생.add(saveStudent);

    }

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 테스트학생.size(); j++) {
        Post post = new Post().builder()
                              .subject(테스트학생.get(j) + " 테스트 제목" + i + j)
                              .postContent(heavy)
                              .student(테스트학생.get(j))
                              .postDate(LocalDateTime.now())
                              .view(0)
                              .build();

        boardRepository.savePost(post);
      }

    }

    em.clear();
    Long start1 = System.currentTimeMillis();
    List<PostListDto> result = boardRepository.findPosts(0);

    for (int i = 0; i < result.size(); i++) {
      PostListDto dto = result.get(i);
      String sResult = "DTO: " + dto.toString();
      System.out.println(sResult);
    }
    em.clear();
    Long end1 = System.currentTimeMillis();
    Long t1 = end1 - start1;
    System.out.println("DTO: " + t1);

    // Long start2 = System.currentTimeMillis();
    // List<Post> result2 = boardRepository.findPostsEntity();

    // for (int i = 0; i < result2.size(); i++) {
    // Post dto = result2.get(i);
    // String sResult2 = "ENTITY: " + dto.toString();
    // }
    // em.clear();
    // Long end2 = System.currentTimeMillis();

    // Long t2 = end2 - start2;
    // System.out.println("Entity: " + t2);

    // Long start3 = System.currentTimeMillis();
    // List<Post> result3 = boardRepository.findPostsFetch();

    // for (int i = 0; i < result3.size(); i++) {
    // Post dto = result3.get(i);
    // String sResult3 = "FETCH: " + dto.toString();
    // }
    // em.clear();
    // Long end3 = System.currentTimeMillis();
    // Long t3 = end3 - start3;
    // System.out.println("Fetch: " + t3);

  }

}
