package team.broadcast.domain.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.broadcast.domain.jwt.refresh.RefreshToken;
import team.broadcast.domain.jwt.refresh.RefreshTokenRepository;
import team.broadcast.domain.user.User;
import team.broadcast.domain.user.UserRepository;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

  private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
  private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

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
   * @param email        user email address
   * @param refreshToken old refreshToken
   */
  public void updateRefreshToken(String email, String refreshToken) {
    User findUser = userRepository.findByEmail(email) // 이메일을 통한 사용자 찾기
        .orElseThrow();

    RefreshToken findRefreshToken = refreshTokenRepository.findByUserId(Long.valueOf(findUser.getId()))
        .orElse(new RefreshToken()); // RefreshToken 없는 경우 새로 발급할 수 있도록 해야 한다.

    findRefreshToken.setRefreshToken(refreshToken);
    refreshTokenRepository.save(findRefreshToken); // RefreshToken DB에 저장
  }

  /**
   *
   * @param token Retrieves an existing token when attempting to log in or issuing a new token.
   * @return Returns true if this is a valid token, false otherwise.
   */
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error("유효하지 않는 토큰입니다. {}", e.getMessage());
      return false;
    }
  }
}
