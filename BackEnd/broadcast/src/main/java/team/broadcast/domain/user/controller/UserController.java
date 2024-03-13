package team.broadcast.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.broadcast.domain.user.dto.UserDto;
import team.broadcast.domain.user.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/signup")
    public String signup(@RequestBody UserDto userDto) {
        try {
            log.info("user={}", userDto);
            userService.join(userDto);
            return "ok"; // 성공시 Response
        } catch (Exception e) {
            return e.getMessage(); // 실패시 에러 메시지 전달
        }
    }
}
