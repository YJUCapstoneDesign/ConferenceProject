package team.broadcast.domain.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import team.broadcast.domain.jwt.JwtProvider.JwtCode;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtVerifyFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request, "Authorization");

        // access token 검증
        if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken) == JwtCode.ACCESS) {
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication); // security context에 인증 정보 저장
        } else if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken) == JwtCode.EXPIRED) {
            log.info("Access token expired");

            String refreshToken = null;
            if (StringUtils.hasText(request.getHeader("Auth"))) { // Auth에는 userId가 담겨 있음
                Long userId = Long.parseLong(request.getHeader("Auth"));
                refreshToken = jwtProvider.getRefreshToken(userId); // userId로 refreshToken 조회
            }

            // refresh token 검증
            if (StringUtils.hasText(refreshToken) && jwtProvider.validateToken(refreshToken) == JwtCode.ACCESS) {
                // access token 재발급
                Authentication authentication = jwtProvider.getAuthentication(refreshToken);
                String newAccessToken = jwtProvider.generateAccessToken(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);
                log.info("Reissue access token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
