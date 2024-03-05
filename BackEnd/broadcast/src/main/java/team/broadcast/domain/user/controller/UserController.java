package team.broadcast.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.broadcast.domain.user.dto.UserDto;
import team.broadcast.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/signup")
    public String signup(@RequestBody UserDto userDto) {
        try {
            userService.join(userDto);
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
