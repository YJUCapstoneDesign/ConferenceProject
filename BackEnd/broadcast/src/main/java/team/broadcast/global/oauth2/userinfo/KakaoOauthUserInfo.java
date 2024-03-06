package team.broadcast.global.oauth2.userinfo;

import java.util.Map;

public class KakaoOauthUserInfo extends OAuthUserInfo {
  private static Map<String, Object> account;
  private static Map<String, Object> profile;

  public KakaoOauthUserInfo(Map<String, Object> attributes) {
    super(attributes);
    account = (Map<String, Object>) attributes.get("kakao_account");
    profile = (Map<String, Object>) account.get("profile");
  }

  @Override
  public String getId() {
    return String.valueOf(attributes.get("id"));
  }

  @Override
  public String getNickname() {
    return String.valueOf(profile.get("nickname"));
  }

  @Override
  public String getEmail() {
    return String.valueOf(account.get("email"));
  }
}