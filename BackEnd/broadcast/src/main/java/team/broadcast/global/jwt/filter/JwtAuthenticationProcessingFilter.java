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
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.filter.OncePerRequestFilter;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.login.user.CustomUserDetails;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
    private static final List<String> NO_CHECK_URLS = List.of("/api/login", "/api/logout");

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (NO_CHECK_URLS.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response); // /login 호출이 들어오면 다음 필터 호출
            return;
        }

        // 사용자 요청 헤더에서 refreshToken 추출
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        log.info("received refresh token: {}", refreshToken);

        // 1. RefreshToken 있는 경우
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
        }

        // 2. RefreshToken 없는 경우
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }

    // refresh token 검증후 재발급
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByToken(refreshToken)
                .ifPresent(user -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    jwtService.sendAccessTokenAndRefreshToken(response, jwtService.generateAccessToken(user.getEmail()),
                            reIssuedRefreshToken);
                });
    }

    // 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.generateRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(jwtService::extractEmail)
                .flatMap(userRepository::findByEmail)
                .ifPresent(this::saveAuthentication);
        filterChain.doFilter(request, response);
    }


    public void saveAuthentication(User myUser) {
        String password = myUser.getPwd();
        // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
        if (password == null) {
            password = KeyGenerators.string().generateKey();
            myUser.updatePassword(password);
        }

        CustomUserDetails userDetails = new CustomUserDetails(myUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
