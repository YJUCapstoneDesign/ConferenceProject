package team.broadcast.domain.oauth2.userinfo;

import java.util.Map;

public class NaverOAuthUserInfo extends OAuthUserInfo {

    private final Map<String, Object> response;

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

    // 휴대폰 로직 가져오기
    public String getMobile() {
        return String.valueOf(response.get("mobile"));
    }
}
