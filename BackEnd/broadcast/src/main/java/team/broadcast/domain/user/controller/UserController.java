package team.broadcast.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.global.login.user.CustomUserDetails;

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

    @GetMapping("/profile")
    public UserResponse getUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserProfile(user.getId());
    }

    @PutMapping("/edit")
    public Long update(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UpdateUser user) {
        return userService.update(userDetails.getEmail(), user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getEmail());
    }

}
