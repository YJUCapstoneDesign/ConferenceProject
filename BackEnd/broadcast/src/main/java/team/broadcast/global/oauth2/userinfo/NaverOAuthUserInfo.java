package team.broadcast.global.oauth2.userinfo;

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
    public String getEmail() {
        return String.valueOf(response.get("email"));
    }

    @Override
    public String getProfileImageUrl() {
        String profileImageUrl = String.valueOf(response.get("profile_image_url"));

        // 프로필 이미지가 없는 경우 기본 이미지 주소를 가져온다.
        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            return getDefaultImageUrl();
        }
        return profileImageUrl;
    }

    // 휴대폰 로직 가져오기
    public String getMobile() {
        return String.valueOf(response.get("mobile"));
    }
}
