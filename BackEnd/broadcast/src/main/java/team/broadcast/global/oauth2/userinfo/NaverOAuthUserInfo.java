package team.broadcast.global.oauth2.userinfo;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class NaverOAuthUserInfo extends OAuthUserInfo {

    private final Map<String, Object> response;

    @Value("${default.image.address}")
    private String defaultImageUrl;

    public NaverOAuthUserInfo(Map<String, Object> attributes) {
        super(attributes);
        response = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getId() {
        return String.valueOf(response.get("id"));
    }

    @Override
    public String getNickname() {
        return String.valueOf(response.get("nickname"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(response.get("email"));
    }

    @Override
    public String getProfileImageUrl() {
        String profileImageUrl = String.valueOf(response.get("profile_image_url"));
        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            return defaultImageUrl;
        }
        return profileImageUrl;
    }

    // 휴대폰 로직 가져오기
    public String getMobile() {
        return String.valueOf(response.get("mobile"));
    }
}
