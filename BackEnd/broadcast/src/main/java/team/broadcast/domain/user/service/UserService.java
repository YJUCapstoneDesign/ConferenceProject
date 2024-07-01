package team.broadcast.domain.user.service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enumstore.enums.UserRole;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserDetailResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.mail.MailService;
import team.broadcast.global.mail.dto.EmailMessage;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

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
                userDto.getPhone());

        userRepository.save(user);
        return user.getId();
    }

    public UserDetailResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return UserDetailResponse.from(user);
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


    public String getUserImage(User user) {
        return user.getImageUrl();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void sendResetPasswordEmail(String email) {
        log.info("email={}", email);
        User user = findUserByEmail(email);
        if (user.getPlatform() != null) {
            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }

        String newPassword = generateRandomPassword(6);

        Map<String, String> html = new HashMap<>();
        html.put("email", user.getEmail());
        html.put("password", newPassword);

        EmailMessage message = EmailMessage.builder()
                .to(user.getEmail())
                .subject("[UNMUTE] 임시 비밀번호 발급")
                .build();

        try {
            mailService.sendMail(message, html);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }

        // 이메일을 보낸 후 비밀번호 업데이트 처리
        user.updatePassword(passwordEncoder.encode(newPassword));

        userRepository.save(user); // 비밀번호 업데이트 저장
    }

    public String generateRandomPassword(int passwordLength) {
        return RandomStringUtils.random(passwordLength, true, true); // 랜덤한 문자열 반환
    }
}
