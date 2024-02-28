package team.broadcast.domain.oauth2;

import lombok.Builder;
import lombok.Getter;
import team.broadcast.domain.enums.Membership;
import team.broadcast.domain.enums.SocialType;
import team.broadcast.domain.enums.UserRole;
import team.broadcast.domain.oauth2.userinfo.GoogleOAuthUserInfo;
import team.broadcast.domain.oauth2.userinfo.KakaoOauthUserInfo;
import team.broadcast.domain.oauth2.userinfo.OAuthUserInfo;
import team.broadcast.domain.user.User;

import java.util.Map;

// 각 소셜에 따라 맞게 받는 데이터를 분리하여 처리하기 위한 DTO
@Getter
public class OAuthAttributes {
  private String nameAttributeKey;
  private OAuthUserInfo oAuthUserInfo; // 소셜 타입별 로그인 유저 정보


  @Builder
  private OAuthAttributes(String nameAttributeKey, OAuthUserInfo oAuthUserInfo) {
    this.nameAttributeKey = nameAttributeKey;
    this.oAuthUserInfo = oAuthUserInfo;
  }

  public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
    if (socialType == SocialType.KAKAO) {
      return ofKakao(userNameAttributeName, attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oAuthUserInfo(new KakaoOauthUserInfo(attributes))
        .build();
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oAuthUserInfo(new GoogleOAuthUserInfo(attributes))
        .build();
  }

  // User 객체 생성
  public User toEntity(SocialType socialType, OAuthUserInfo oauth2UserInfo) {
    return User.builder()
        .platform(socialType.name())
        .id(oauth2UserInfo.getId())
        .email(oauth2UserInfo.getEmail())
        .nickname(oauth2UserInfo.getNickname())
        .admin(UserRole.USER)
        .membership(Membership.BASIC)
        .build();
  }

}
