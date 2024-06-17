package jpapractice.jpapractice.service;

import jakarta.transaction.Transactional;
import jpapractice.jpapractice.customException.DataNotFoundException;
import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.ClubDto;
import jpapractice.jpapractice.repository.ClubRepository;
import jpapractice.jpapractice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    private static final String UPLOAD_DIR = "static/images/";

    public Club saveClub(ClubDto clubDto, MultipartFile image, String accountId) throws IOException {
        String imagePath = saveImage(image);
        Student student = memberRepository.findByAccountId(accountId).orElseThrow();

        Club club = Club.builder()
                .name(clubDto.getName())
                .description(clubDto.getDescription())
                .imageUrl(imagePath)
                .build();

        student.addClub(club); // 학생에 클럽 추가



        Club saved = clubRepository.save(club); // 클럽 저장
        memberRepository.save(student); // 학생 저장 (사실상 필요없을 수 있음, 클럽 저장시 cascade에 의해 저장될 수 있음)

        return saved;
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id).orElseThrow();
    }

    public void updateClub(Long id, ClubDto clubDto, MultipartFile image) throws IOException {
        Club club = getClubById(id);
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            club.setImageUrl(imagePath);
        }
        club.setName(clubDto.getName());
        club.setDescription(clubDto.getDescription());
        clubRepository.save(club);
    }

//    public void deleteClub(Long id) {
//        clubRepository.deleteById(id);
//    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }

        File uploadDir = new ClassPathResource(UPLOAD_DIR).getFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String imageName = image.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir.getAbsolutePath(), imageName);
        image.transferTo(imagePath.toFile());
        return "/images/" + imageName;
    }


    @Transactional
    public void joinClub(String accountId, Long clubId) {
        Student student = memberRepository.findByAccountId(accountId)
                .orElseThrow(() -> new DataNotFoundException("Student not found"));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));

        if (student.getClubs().contains(club)) {
            throw new IllegalArgumentException("Already a member of this club");
        }

        student.getClubs().add(club);
        memberRepository.save(student);
    }

    @Transactional
    public void deleteClub(String accountId, Long clubId) {
        Student student = memberRepository.findByAccountId(accountId)
                .orElseThrow(() -> new DataNotFoundException("Student not found"));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));

        log.info("students: {}", club.getStudents());

        student.getClubs().remove(club);
        club.getStudents().remove(student);

        // 클럽에 더 이상 멤버가 없으면 클럽 삭제, 그렇지 않으면 저장
        if (club.getStudents().isEmpty()) {
            clubRepository.delete(club);
        } else {
            clubRepository.save(club);
        }

        // 학생의 클럽 목록을 업데이트하고 저장
        memberRepository.save(student);
    }

    public Club updateClub(Long clubId, ClubDto dto) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new DataNotFoundException("Club not found"));

        club.setName(dto.getName());
        club.setDescription(dto.getDescription());

        return clubRepository.save(club);
    }

}
