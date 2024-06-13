package jpapractice.jpapractice.repository;

import jpapractice.jpapractice.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByAccountId(String accountId);
}
