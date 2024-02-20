package team.broadcast.global.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team.broadcast.domain.jwt.JwtProvider;
import team.broadcast.domain.user.PrincipalDetail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
  private final JwtProvider jwtProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

    log.info("authentication.getPrincipal() = {}", principal);

    Map<String, Object> responseMap = principal.getUserInfo();
    responseMap.put("accessToken", jwtProvider.generateAccessToken(authentication));
    responseMap.put("refreshToken", jwtProvider.generateRefreshToken(authentication));

    Gson gson = new Gson();
    String json = gson.toJson(responseMap);
    log.info("result={}", json); // 그저 생성한 것을 보여 주고 있음

    response.setContentType("application/json; charset=UTF-8");
    PrintWriter writer = response.getWriter();
    writer.println(json);
    writer.flush();
  }
}
