package team.broadcast.global.oauth2.userinfo;

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
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getProfileImageUrl() {
        return String.valueOf(attributes.get("picture"));
    }
}
