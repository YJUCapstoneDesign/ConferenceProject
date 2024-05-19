package team.broadcast.global.logout.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.jwt.exception.JwtErrorCode;
import team.broadcast.global.jwt.service.JwtService;

import java.util.Optional;

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

        Optional<String> token = jwtService.extractRefreshToken(request);

        if (token.isEmpty()) {
            throw new CustomException(JwtErrorCode.NOT_FOUND_REFRESH);
        }

        String refreshToken = token.get();
        jwtService.isTokenValid(refreshToken);

        // 세션 무효화
        HttpSession session = request.getSession();
        session.invalidate();

        log.info("refresh token: {}", refreshToken);

        // 보안을 위해 로그아웃에서는 데이터베이스에 저장된 refreshToken 삭제
        jwtService.expireRefreshToken(refreshToken);


    }
}
