package team.broadcast.global.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import team.broadcast.global.exception.ErrorResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Value("${client.fail-url}")
    private String failUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .code("INVALID_AUTH_REQUEST")
                .reason("로그인에 실패했습니다.")
                .build();

        // 인증 실패시 메인 페이지로 이동
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        log.error(exception.getMessage());
        objectMapper.writeValue(response.getWriter(), errorResponse);

        String targetUri = UriComponentsBuilder.fromUriString(failUrl)
                .build().toUriString();

        response.sendRedirect(targetUri);
    }
}
