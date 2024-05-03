package team.broadcast.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.global.login.user.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "회원 관리 API", description = "회원 정보 API")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
            summary = "유저 회원가입",
            description = "회원가입에는 JWT 토큰이 필요하지 않으며 아래와 같은 형식의 데이터를 받는다.")
    public String signup(@RequestBody @Valid SignupUser user) {
        userService.join(user);
        return "success";
    }

    @GetMapping("/profile")
    @Operation(summary = "회원 정보 상세조회", description = "회원의 정보를 상세 조회 API")
    public UserResponse getUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserProfile(user.getId());
    }

    @PutMapping("/update")
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정에 사용하는 API")
    public Long update(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UpdateUser user) {
        return userService.update(userDetails.getEmail(), user);
    }

    @PutMapping("/update/profileImage")
    @Operation(summary = "프로필 이미지 수정", description = "이미지 경로를 통해 프로필 이미지 수정")
    public Long updateProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails, MultipartFile profileImage) {
        return userService.updateProfileImage(userDetails.getId(), profileImage);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
    public void deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getEmail());
    }

}
