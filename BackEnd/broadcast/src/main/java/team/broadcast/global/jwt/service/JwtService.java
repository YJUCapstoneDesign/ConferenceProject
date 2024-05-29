package team.broadcast.global.jwt.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.broadcast.domain.user.entity.User;
import team.broadcast.domain.user.repository.UserRepository;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
@Getter
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer ";

    private final UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;
    @Value("${jwt.access.header}")
    private String accessTokenHeader;
    @Value("${jwt.refresh.header}")
    private String refreshTokenHeader;
    private Key key;

    @PostConstruct
    protected void init() {
        // key를 base64로 인코딩
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    /**
     * @param email user email address.
     * @return new accessToken
     * @NOTE AccessToken Generate Method
     */
    public String generateAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @return new refreshToken
     * @NOTE RefreshToken Generate Method
     */
    public String generateRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @param response    Response information to send to the client
     * @param accessToken AccessToken to send to the client
     * @NOTE This method is used when the AccessToken has expired and the RefreshToken has not expired.
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(accessTokenHeader, accessToken); // 헤더에 실어서 응답을 한다.
    }

    /**
     * @param response     Response information to send to the client
     * @param accessToken  AccessToken to send to the client
     * @param refreshToken RefreshToken for reissuing AccessToken.
     * @NOTE RefreshToken is stored in DB so that when this RefreshToken expires, it can be reissued again.
     */
    public void sendAccessTokenAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(accessTokenHeader, accessToken);
        response.addHeader(refreshTokenHeader, refreshToken);
    }

    /**
     * @param email        user email address
     * @param refreshToken refreshToken 업데이트 토큰
     */
    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> {
                            user.updateRefreshToken(refreshToken);
                            userRepository.save(user);
                        },
                        () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
                );
    }

    /**
     * @param token Retrieves an existing token when attempting to log in or issuing a new token.
     * @return Returns true if this is a valid token, false otherwise.
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
            throw new JwtException("유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException e) {
            log.error("expired jwt token");
            throw new JwtException("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            log.error("unsupported jwt token");
        } catch (IllegalArgumentException e) {
            log.error("illegal jwt token");
            throw new JwtException("유효하지 않는 compact JWT 토큰");
        }
        return false;
    }


    /**
     * @param request
     * @return RefreshToken string with the "Bearer " part removed from the RefreshToken in the request header
     * @NOTE extract the RefreshToken contained in the client request header
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshTokenHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
            return Optional.ofNullable(claims.getBody().get("email", String.class));
        } catch (Exception e) {
            log.error("엑세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessTokenHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public String getRefreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return user.getToken();
    }

    public void expireRefreshToken(String refreshToken) {
        User user = userRepository.findByToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        user.deleteRefreshToken();
        userRepository.save(user);
    }
}
