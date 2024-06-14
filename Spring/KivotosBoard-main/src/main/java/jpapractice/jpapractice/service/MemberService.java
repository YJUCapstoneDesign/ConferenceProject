package jpapractice.jpapractice.service;

import jakarta.transaction.Transactional;
import jpapractice.jpapractice.customException.DataNotFoundException;
import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.member.DefaultInfoDto;
import jpapractice.jpapractice.dto.member.StudentAndAccountDto;
import jpapractice.jpapractice.repository.ClubRepository;
import jpapractice.jpapractice.repository.CommentRepository;
import jpapractice.jpapractice.repository.MemberRepository;
import jpapractice.jpapractice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClubRepository clubRepository;

    @Transactional
    public String registMember(StudentAndAccountDto studentAndAccountDto) {
        Student studentInfo = Student.builder()
                .name(studentAndAccountDto.getName())
                .accountId(studentAndAccountDto.getAccountId())
                .passwd(passwordEncoder.encode(studentAndAccountDto.getAccountPasswd()))
                .age(studentAndAccountDto.getAge())
                .email(studentAndAccountDto.getEmail())
                .build();

        Student student = memberRepository.save(studentInfo);

        return student.getAccountId();
    }

    public DefaultInfoDto findInfo(String accountId) {
        Optional<Student> result = memberRepository.findByAccountId(accountId);
        if (result.isEmpty()) {
            throw new DataNotFoundException("account not found");
        }
        Student student = result.get();
        return DefaultInfoDto.builder()
                .id(student.getAccountId())
                .studentName(student.getName())
                .age(student.getAge())
                .email(student.getEmail())
                .build();
    }

    public List<Club> findClubs(String accountId) {
        Optional<Student> result = memberRepository.findByAccountId(accountId);
        if (result.isEmpty()) {
            throw new DataNotFoundException("account not found");
        }

        Student student = result.get();
        return new ArrayList<>(student.getClubs());
    }

    @Transactional
    public StudentAndAccountDto getUserInfoForModify(String accountId) {
        Optional<Student> optionalAccount = memberRepository.findByAccountId(accountId);
        if (optionalAccount.isEmpty()) {
            throw new DataNotFoundException("사용자가 존재하지 않습니다.");
        }
        Student student = optionalAccount.get();
        StudentAndAccountDto studentAndAccountDto = new StudentAndAccountDto();
        studentAndAccountDto.setAccountId(student.getAccountId());
        studentAndAccountDto.setEmail(student.getEmail());
        studentAndAccountDto.setAge(student.getAge());
        studentAndAccountDto.setName(student.getName());
        return studentAndAccountDto;
    }

    @Transactional
    public String modifyInfo(StudentAndAccountDto studentAndAccountDto,
                             String accountId) {
        Optional<Student> optionalAccount = memberRepository.findByAccountId(accountId);
        if (optionalAccount.isEmpty()) {
            throw new DataNotFoundException("해당하는 사용자가 없습니다.");
        } else {
            Student student = optionalAccount.get();
            student.changePasswd(
                    passwordEncoder.encode(studentAndAccountDto.getAccountPasswd()));
            student.changeAge(studentAndAccountDto.getAge());
            student.changeEmail(studentAndAccountDto.getEmail());
            return accountId;
        }
    }

    @Transactional
    public String getPassword(String accountId) {
        Student student = memberRepository.findByAccountId(accountId)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                "사용자가 존재하지 않습니다"));
        return student.getPasswd();
    }

    @Transactional
    public void unregistMember(String accountId) {
        Student student = memberRepository.findByAccountId(accountId)
                .orElseThrow(() -> new DataNotFoundException("계정을 찾을 수 없습니다."));

        commentRepository.deleteByStudent(student);

        // 연관된 게시글 삭제
        boardRepository.deleteByStudent(student);

        List<Club> clubs = student.getClubs();

        for (Club club : clubs) {
            student.getClubs().remove(club);
            clubRepository.delete(club);
        }

        // 최종적으로 학생 삭제
        memberRepository.delete(student);
    }

}
