package team.broadcast.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid SignupUser user) {
        userService.join(user);
        return "success";
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }

    @PostMapping("/{userId}/edit")
    public Long update(@PathVariable Long userId, @RequestBody @Valid UpdateUser user) {
        return userService.update(userId, user);
    }

}
