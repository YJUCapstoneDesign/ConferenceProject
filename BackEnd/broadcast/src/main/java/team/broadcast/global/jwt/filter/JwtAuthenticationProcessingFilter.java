package team.broadcast.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.filter.OncePerRequestFilter;
import team.broadcast.global.jwt.refresh.RefreshToken;
import team.broadcast.global.jwt.refresh.RefreshTokenRepository;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;

import java.io.IOException;

import static org.springframework.security.core.userdetails.User.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
    private static final String NO_CHECK_URL = "/login";

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response); // /login 호출이 들어오면 다음 필터 호출
            return;
        }

        // 사용자 요청 헤더에서 refreshToken 추출
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        // 1. RefreshToken 있는 경우
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
        }

        // 2. RefreshToken 없는 경우
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(token -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(token);
                    User user = userRepository.findById(token.getId()).orElse(null);
                    jwtService.sendAccessTokenAndRefreshToken(response, jwtService.generateAccessToken(user.getEmail()), reIssuedRefreshToken);
                });
    }

    // 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드
    private String reIssueRefreshToken(RefreshToken token) {
        String reIssuedRefreshToken = jwtService.generateRefreshToken();
        token.setToken(reIssuedRefreshToken);
        refreshTokenRepository.saveAndFlush(token);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> userRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));
        filterChain.doFilter(request, response);
    }

    /* 다음 아래 코드가 맞는지 생각을 해야 한다. 내가 원하는 방식이 되도록 노력하자! */
    public void saveAuthentication(User myUser) {
        String password = myUser.getPwd();
        // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
        if (password == null) {
            password = KeyGenerators.string().generateKey();
        }

        UserDetails userDetails = builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getAdmin().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
