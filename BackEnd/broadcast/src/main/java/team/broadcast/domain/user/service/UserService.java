package team.broadcast.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.broadcast.domain.enumstore.enums.UserRole;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.global.exception.CustomException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.image.address}")
    private String defaultImageAddress;

    @Transactional
    public Long createUser(SignupUser userDto) {
        // 이메일 중복 검사.
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new CustomException(UserErrorCode.DUPLICATED_EMAIL);
        }

        User newUser = User.builder()
                .name(userDto.getUsername())
                // 닉네임 같은 경우 초기에 랜덤한 길이의 문자열로 생성되도록 하였다.
                .nickname(generateRandomName(15))
                // 비밀번호를 암호화해서 저장
                .pwd(passwordEncoder.encode(userDto.getPassword()))
                .imageUrl(defaultImageAddress)
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .admin(UserRole.USER)
                .build();

        log.info("user={}", newUser);

        userRepository.save(newUser);
        return newUser.getId();
    }

    @Transactional
    public Long update(String email, UpdateUser userDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        user.changeUserInfo(userDto.getUsername(),
                userDto.getNickname(),
                // 업데이트 할 때 암호화할 수 있도록 한다.
                userDto.getPhone());

        userRepository.save(user);
        return user.getId();
    }

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }

    @Transactional
    public void updatePassword(String email, String oldPassword, String newPassword) {
        // email 통해 user 검색
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 입력한 비밀번호가 같은지 검사를 한다..
        if (!passwordEncoder.matches(oldPassword, findUser.getPwd())) {
            throw new CustomException(UserErrorCode.INVALID_PASSWORD);
        }

        // 새로운 비밀번호로 업데이트 한다.
        findUser.updatePassword(passwordEncoder.encode(newPassword));
        userRepository.save(findUser);
    }

    @Transactional
    public Long updateProfileImage(Long userId, MultipartFile file) {
        // 파일 이름을 랜덤으로 지은다.
        String imageFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destinationFile = new File("/images/profile" + imageFileName);

        try {
            file.transferTo(destinationFile);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

            user.updateImageUrl("/images/profile" + imageFileName);
            userRepository.save(user);
            return user.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    // 지정한 수 만큼의 길이를 가지는 문자와 숫자로 구성된 랜덤한 이름을 생성
    public String generateRandomName(int nameLength) {
        return RandomStringUtils.random(nameLength, true, true);
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }
}
