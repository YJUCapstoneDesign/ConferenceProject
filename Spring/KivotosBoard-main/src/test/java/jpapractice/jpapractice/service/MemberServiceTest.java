package jpapractice.jpapractice.service;

import jakarta.persistence.EntityManager;
import jpapractice.jpapractice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  EntityManager em;

  @Autowired
  private MemberRepository memberRepository;

  // @Transactional
  // @Test
  // public void AccountTest() {

  // System.out.println("AccountTest 시작");

  // em.clear();

  // Long start1 = System.currentTimeMillis();

  // Account account = memberRepository.findStudentById("mirror111").get();

  // DefaultInfoDto dto = new DefaultInfoDto();

  // dto.setStudentName(account.getStudent().getName());
  // dto.setSchoolName(account.getStudent().getSchool().getSchoolName());
  // dto.setClubName(account.getStudent().getClub().getName());
  // dto.setPositionName(account.getStudent().getPosition().getName());
  // dto.setStudentType(account.getStudent().getType().name());

  // System.out.println("dto1: " + dto.toString());

  // Long end1 = System.currentTimeMillis();

  // // Assertions.assertThat(account.getId()).isEqualTo("mirror111");

  // em.flush();
  // em.clear();

  // System.out.println("직접 만든 쿼리 실행 시간: " + (end1 - start1));

  // System.out.println("AccountTest 끝");
  // }

}
