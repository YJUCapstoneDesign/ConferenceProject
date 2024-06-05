package team.broadcast.global.oauth2.userinfo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public abstract class OAuthUserInfo {
  protected Map<String, Object> attributes;

  @Getter
  @Value("${default.image.address}")
  private String defaultImageUrl;

  public OAuthUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

    public abstract String getId(); // 소셜 식별 값

  public abstract String getEmail();

  public abstract String getProfileImageUrl();
}
