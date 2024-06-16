package team.broadcast.global.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponentsBuilder;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.oauth2.CustomOAuth2User;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler, WebMvcConfigurer {

    private final JwtService jwtService;

    @Value("${client.success-url}")
    private String successUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 사용자 정보를 가져 온다.
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        log.info("oAuth2User={}", oAuth2User);

        sendToken(response, oAuth2User); // 로그인에 성공한 경우 AccessToken, RefreshToken 생성
    }

    // RefreshToken 유/무에 따라 accessToken 만 보내거나 RefreshToken 과 같이 보내게 된디.
    private void sendToken(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String email = oAuth2User.getEmail();
        String userRefreshToken = jwtService.getRefreshToken(email);
        String accessToken = jwtService.generateAccessToken(email);
        response.addHeader(jwtService.getAccessTokenHeader(), "Bearer " + accessToken);


        // refresh token이 있는 경우 accessToken만 보내도록 한다.
        if (userRefreshToken != null) {
            log.info("User RefreshToken={}", userRefreshToken);
            jwtService.sendAccessToken(response, accessToken);
        } else {
            String refreshToken = jwtService.generateRefreshToken();
            response.addHeader(jwtService.getRefreshTokenHeader(), "Bearer " + refreshToken);
            jwtService.sendAccessTokenAndRefreshToken(response, accessToken, refreshToken);
            jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
        }

        String targetUri = UriComponentsBuilder.fromUriString(successUrl)
                .build().toUriString();

        response.sendRedirect(targetUri);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**") // 해당 경로 변경 감지
                .addResourceLocations("file:src/main/resources/static/");
    }
}
