package jpapractice.jpapractice.repository;

import jakarta.persistence.EntityManager;
import java.util.Optional;
import jpapractice.jpapractice.domain.Account;
import jpapractice.jpapractice.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

  private final EntityManager em;

  @Autowired
  public MemberRepository(EntityManager em) {
    this.em = em;
  }

  public Student saveStudent(Student student) {

    em.persist(student);

    return student;
  }

  public Account saveAccount(Account account) {

    em.persist(account);

    return account;

  }

  public Optional<Account> accountFindById(String id) {
    Account result = em.find(Account.class, id);
    // System.out.println(result);
    return Optional.ofNullable(result);
  }

  public void removeStudentInfo(Account account) {
    Student student = account.getStudent();
    em.remove(student);
  }

  public void unregistMember(Account account) {
    em.remove(account);
  }
}
