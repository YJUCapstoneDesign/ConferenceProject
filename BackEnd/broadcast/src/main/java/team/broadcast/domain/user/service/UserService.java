package team.broadcast.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enumstore.enums.Membership;
import team.broadcast.domain.enumstore.enums.UserRole;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.global.exception.CustomException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(SignupUser userDto) {
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
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .admin(UserRole.USER)
                .membership(Membership.BASIC)
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
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getPhone());

        userRepository.save(user);
        return user.getId();
    }

    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        return UserResponse.builder()
                .username(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .membership(user.getMembership())
                .build();
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
