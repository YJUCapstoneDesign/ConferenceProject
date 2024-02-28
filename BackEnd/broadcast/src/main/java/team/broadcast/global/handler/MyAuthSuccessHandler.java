package team.broadcast.global.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.broadcast.domain.jwt.JwtProvider;
import team.broadcast.domain.oauth2.CustomOAuth2User;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    // 사용자 정보를 가져 온다.
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    log.info("oAuth2User={}", oAuth2User);

    String accessToken = jwtProvider.generateAccessToken(authentication);
    String refreshToken = jwtProvider.generateRefreshToken(authentication);
    response.addHeader("Access-Token","Bearer " + accessToken);
    response.addHeader("Refresh-Token","Bearer " + refreshToken);
  }
}
