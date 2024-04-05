package team.broadcast.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        try {
            log.info("user={}", userDto);
            userService.join(userDto);
            return new ResponseEntity<>("ok", HttpStatus.OK); // 성공시 Response
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
