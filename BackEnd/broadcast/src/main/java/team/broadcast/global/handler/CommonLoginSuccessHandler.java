package team.broadcast.global.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team.broadcast.domain.jwt.JwtProvider;
import team.broadcast.domain.jwt.refresh.RefreshToken;
import team.broadcast.domain.user.PrincipalDetail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        log.info("authentication.getPrincipal() = {}", principal);

        Map<String, Object> responseMap = principal.getUserInfo();
        // accessTokenHandler가 있어야 한다. 이를 어떻게 해야할까 고민중
        responseMap.put("accessToken", ));
        responseMap.put("refreshToken", JwtUtils.generateToken(responseMap, JwtConstants.REFRESH_EXP_TIME));

        Gson gson = new Gson();
        String json = gson.toJson(responseMap);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.flush();
    }
}
