package team.broadcast.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.broadcast.domain.user.dto.UserResponse;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.exception.UserErrorCode;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;
    private final ObjectMapper objectMapper;

    private String extractEmail(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getEmail();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = jwtService.generateRefreshToken();

        // 사용자가 로그인 할 때 마다  새로운 토큰을 생성하여 발급한다.
        jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        jwtService.updateRefreshToken(user.getEmail(), refreshToken);

        log.info("Login Success. email={}", user.getEmail());
        log.info("Login Success. AccessToken={}", accessToken);
        log.info("accessToken Expiration={}", accessTokenExpiration);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        UserResponse userResponse = UserResponse.from(user);

        objectMapper.writeValue(response.getWriter(), userResponse);
    }
}
