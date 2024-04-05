package team.broadcast.global.login.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.domain.user.mysql.repository.UserRepository;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        String accessToken = jwtService.generateAccessToken(email);
        String refreshToken = jwtService.generateRefreshToken();

        jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);

        userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        jwtService.updateRefreshToken(email, refreshToken);

        log.info("Login Success. email={}", email);
        log.info("Login Success. AccessToken={}", accessToken);
        log.info("accessToken Expiration={}", accessTokenExpiration);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"ok\"}");
    }
}
