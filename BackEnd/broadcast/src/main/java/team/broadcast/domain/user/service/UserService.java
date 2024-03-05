package team.broadcast.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enums.Membership;
import team.broadcast.domain.enums.UserRole;
import team.broadcast.domain.user.User;
import team.broadcast.domain.user.UserRepository;
import team.broadcast.domain.user.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto join(UserDto userDto) throws Exception {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalAccessException("이미 존재하는 회원입니다.");
        }

        User newUser = User.builder()
                .name(userDto.getUsername())
                .nickname(userDto.getNickname())
                // 비밀번호를 암호화해서 저장
                .pwd(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .admin(UserRole.USER)
                .membership(Membership.BASIC)
                .build();

        userRepository.save(newUser);
        return userDto;
    }
}
