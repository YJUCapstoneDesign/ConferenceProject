package team.broadcast.domain.oauth2.userinfo;

import java.util.Map;

public class GoogleOAuthUserInfo extends OAuthUserInfo {
  public GoogleOAuthUserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return String.valueOf(attributes.get("sub"));
  }

  @Override
  public String getNickname() {
    return String.valueOf(attributes.get("name"));
  }

  @Override
  public String getEmail() {
    return String.valueOf(attributes.get("email"));
  }
}
