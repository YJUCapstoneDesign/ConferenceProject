package team.broadcast.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.oauth2.CustomOAuth2User;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 사용자 정보를 가져 온다.
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        log.info("oAuth2User={}", oAuth2User);

        sendToken(response, oAuth2User); // 로그인에 성공한 경우 AccessToken, RefreshToken 생성
    }

    /*
     * TODO : 로그인 성공시 RefreshToken 유/무에 따라 다르게 처리
     * 해당 메서드는 로그인 성공시 accessToken과 RefreshToken을 발급한다.
     * 로그인 한 경우 무조건 둘다 발급 하는 것이 아닌 RefreshToken의 유/무 (+ 만료되었는지)에 따라 다르게 처리해야 한다.
     * 즉 코드를 수정할 필요가 있음.
     */
    private void sendToken(HttpServletResponse response, CustomOAuth2User oAuth2User) {
        String accessToken = jwtService.generateAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.generateRefreshToken();
        response.addHeader(jwtService.getAccessTokenHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshTokenHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
