package team.broadcast.global.logout.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import team.broadcast.global.jwt.service.JwtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtService jwtService;

    // 원칙적으로는 POST 방식으로 로그아웃 요청을 처리하지만
    // SecurityContextLogoutHandler 를 통해 GET 방식으로도 처리가 된다.

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) {

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElseThrow(() -> new IllegalStateException("Invalid refresh token"));

        // 세션 무효화
        HttpSession session = request.getSession();
        session.invalidate();

        log.info("refresh token: {}", refreshToken);

        // 데이터베이스에 저장된 refreshToken 삭제
        jwtService.expireRefreshToken(refreshToken);


    }
}
