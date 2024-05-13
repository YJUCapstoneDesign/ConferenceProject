package team.broadcast.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team.broadcast.global.exception.CustomException;
import team.broadcast.global.exception.ErrorResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomExceptionFilter extends OncePerRequestFilter {

    private static final Map<String, Object> body = new HashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setCustomErrorResponse(request, response, e);
        } catch (Exception e) {
            setErrorResponse(request, response, e);
        }
    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable exception) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        body.put("status", HttpServletResponse.SC_BAD_REQUEST);
        body.put("code", "BASIC-ERROR");
        body.put("message", exception.getMessage());
        body.put("path", request.getServletPath());

        mapper.writeValue(response.getOutputStream(), body);
    }

    public void setCustomErrorResponse(HttpServletRequest request, HttpServletResponse response, CustomException e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = e.getCustomErrorCode().getErrorResponse();
        response.setStatus(errorResponse.getStatus());

        body.put("status", errorResponse.getStatus());
        body.put("code", errorResponse.getCode());
        body.put("message", errorResponse.getReason());
        body.put("path", request.getServletPath());

        mapper.writeValue(response.getOutputStream(), body);

    }
}
