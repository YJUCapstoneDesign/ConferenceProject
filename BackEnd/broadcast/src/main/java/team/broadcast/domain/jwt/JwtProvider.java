package team.broadcast.domain.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.broadcast.domain.jwt.refresh.RefreshToken;
import team.broadcast.domain.jwt.refresh.RefreshTokenRepository;
import team.broadcast.domain.user.User;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
  private final String AUTHORITIES_KEY = "auth";
  private final RefreshTokenRepository refreshTokenRepository;
  @Value("${jwt.access.expiration}")
  private Long accessTokenValidTime; // 30분
  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenValidTime; // 하루
  @Value("${jwt.secretKey}")
  private String secretKey;
  private Key key;

  @PostConstruct
  protected void init() {
    // key를 base64로 인코딩
    String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    key = Keys.hmacShaKeyFor(encodedKey.getBytes());
  }

  /*
  Access Token 발급 코드
  */
  public String generateAccessToken(Authentication authentication) {
    // 인증된 사용자의 권한 목록 조회
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    Date now = new Date();
    Date expiration = new Date(now.getTime() + accessTokenValidTime); // 만료시간

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(now) // 발행 시간
        .setExpiration(expiration) // 만료시간
        .signWith(key, SignatureAlgorithm.HS512) // 비밀키, 해싱 알고리즘
        .compact();
  }

  /*
  Refresh Token 발급 코드
   */
  public String generateRefreshToken(Authentication authentication) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + refreshTokenValidTime); // 리프레시 토큰 만료 시간

    return Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject()); // 여기 부분 수정이 필요하다.
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public JwtCode validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return JwtCode.ACCESS;
    } catch (ExpiredJwtException e) {
      // 만료된 경우에는 refresh token을 확인하기 위해
      return JwtCode.EXPIRED;
    } catch (JwtException | IllegalArgumentException e) {
      log.error("jwtException : {}", e);
    }
    return JwtCode.DENIED;
  }

  @Transactional(readOnly = true)
  public String getRefreshToken(Long userId) {
    RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("refresh token이 존재하지 않습니다."));
    return refreshToken.getRefreshToken();
  }

  public enum JwtCode {
    DENIED,
    EXPIRED,
    ACCESS
  }
}
