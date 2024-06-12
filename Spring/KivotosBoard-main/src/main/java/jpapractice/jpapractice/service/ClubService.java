package jpapractice.jpapractice.service;

import jpapractice.jpapractice.domain.Club;
import jpapractice.jpapractice.dto.board.ClubDto;
import jpapractice.jpapractice.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    // static 디렉토리 하위에 images 디렉토리 설정
    private static final String UPLOAD_DIR = "static/images/";

    public Club saveClub(ClubDto clubDto, MultipartFile image) throws IOException {
        String imagePath = saveImage(image);

        Club club = Club.builder()
                .name(clubDto.getName())
                .description(clubDto.getDescription())
                .imageUrl(imagePath)
                .build();

        return clubRepository.save(club);
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
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
