package team.broadcast.global.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    // Refresh Token을 재발급 하는 api
    @GetMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public String refreshToken() {
        return "토큰이 발급 완료되었습니다.";
    }
}
