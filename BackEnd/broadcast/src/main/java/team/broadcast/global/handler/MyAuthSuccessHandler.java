package team.broadcast.global.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import team.broadcast.domain.jwt.JwtProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private static final String loginSuccessUrl = "http://localhost:8080/loginSuccess";
  private final JwtProvider jwtProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    // 사용자 정보를 가져 온다.
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String email = oAuth2User.getAttribute("email");
    String provider = oAuth2User.getAttribute("provider");

    log.info("email={}", email);
    log.info("provider={}", provider);

    // null 처리를 위해 한다.
    Boolean isExist = oAuth2User.getAttribute("exist");

    if (Boolean.TRUE.equals(isExist)) {
      // 회원이 존재하면 jwt token 발행을 시작한다.
      String accessToken = jwtProvider.generateAccessToken(authentication);
      String refreshToken = jwtProvider.generateRefreshToken(authentication);

      // accessToken을 쿼리스트링에 담는 url을 만들어준다.
      String targetUrl = UriComponentsBuilder.fromUriString(loginSuccessUrl)
          .queryParam("accessToken", accessToken)
          .build()
          .encode(StandardCharsets.UTF_8)
          .toUriString();
      // 로그인 확인 페이지로 리다이렉트 시킨다.
      getRedirectStrategy().sendRedirect(request, response, targetUrl);

    } else {

      // 회원이 존재하지 않을경우, 서비스 제공자와 email을 쿼리스트링으로 전달하는 url을 만들어준다.
      String targetUrl = UriComponentsBuilder.fromUriString(loginSuccessUrl)
          .queryParam("email", (String) oAuth2User.getAttribute("email"))
          .queryParam("provider", provider)
          .build()
          .encode(StandardCharsets.UTF_8)
          .toUriString();
      // 회원가입 페이지로 리다이렉트 시킨다.
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
  }
}
