package team.broadcast.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.broadcast.domain.user.dto.PasswordUpdate;
import team.broadcast.domain.user.dto.SignupUser;
import team.broadcast.domain.user.dto.UpdateUser;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.service.UserService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.util.Map;

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
        userService.createUser(user);
        return "success";
    }

    // 로그인 후
    @GetMapping("/image")
    public String image(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserImage(user.getUser());
    }

    @GetMapping("/profile")
    @Operation(summary = "회원 정보 상세조회", description = "회원의 정보를 상세 조회 API")
    public UserResponse getUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userService.getUserProfile(user.getId());
    }

    @PatchMapping("/update/info")
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정에 사용하는 API")
    public Long update(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UpdateUser user) {
        return userService.update(userDetails.getEmail(), user);
    }

    @PatchMapping("/update/password")
    @Operation(summary = "회원 비밀번호 수정", description = "기존 비밀번호와 새 비밀번호를 입력받아 성공하면 200, 실패하면 400 에러를 반환한다.")
    public void updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody @Valid PasswordUpdate passwordUpdate) {
        userService.updatePassword(userDetails.getEmail(),
                passwordUpdate.getCurrentPassword(), passwordUpdate.getNewPassword());
    }

//    @PutMapping("/update/profileImage")
//    @Operation(summary = "프로필 이미지 수정", description = "이미지 경로를 통해 프로필 이미지 수정")
//    public Long updateProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails, MultipartFile profileImage) {
//        return userService.updateProfileImage(userDetails.getId(), profileImage);
//    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
    public void deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getEmail());
    }

    @PostMapping("/find-pwd")
    @Operation(summary = "임시비밀번호 발급", description = "비밀번호를 잊어버릴 경우 이메일로 임시 비밀번호를 발급한다.")
    public void sendResetPassword(@RequestBody Map<String, String> email) {
        userService.sendResetPasswordEmail(email.get("email"));
    }

}
