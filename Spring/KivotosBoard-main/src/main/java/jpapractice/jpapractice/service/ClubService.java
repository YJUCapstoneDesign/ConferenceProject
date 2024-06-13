package jpapractice.jpapractice.service;

import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.ClubDto;
import jpapractice.jpapractice.repository.ClubRepository;
import jpapractice.jpapractice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
                .student(student)
                .build();

        return clubRepository.save(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id).orElseThrow();
    }

    public void updateClub(Long id, ClubDto clubDto, MultipartFile image) throws IOException {
        Club club = getClubById(id);
        if (!image.isEmpty()) {
            String imagePath = saveImage(image);
            club.setImageUrl(imagePath);
        }
        club.setName(clubDto.getName());
        club.setDescription(clubDto.getDescription());
        clubRepository.save(club);
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

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
}
