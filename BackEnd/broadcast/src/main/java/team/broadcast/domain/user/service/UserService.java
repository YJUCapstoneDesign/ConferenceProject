package team.broadcast.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.user.enums.Membership;
import team.broadcast.domain.user.enums.UserRole;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.domain.user.dto.UserDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto join(UserDto userDto) throws Exception {
        if (userDto.getEmail() == null) {
            throw new IllegalAccessException("데이터가 들어 오지 않았습니다.");
        }

        // 이메일 중복 검사.
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalAccessException("이미 존재하는 회원입니다.");
        }

        User newUser = User.builder()
                .name(userDto.getUsername())
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
        return userDto;
    }

    // 지정한 수 만큼의 길이를 가지는 문자와 숫자로 구성된 랜덤한 이름을 생성
    public String generateRandomName(int nameLength) {
        return RandomStringUtils.random(nameLength, true, true);
    }
}
