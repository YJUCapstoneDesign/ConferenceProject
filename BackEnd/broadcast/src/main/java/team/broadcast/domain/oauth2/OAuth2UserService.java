package team.broadcast.domain.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.broadcast.domain.kakao.KakaoUserInfo;
import team.broadcast.domain.user.User;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("oAuth2User={}", oAuth2User);
        log.info("attributes={}",attributes);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        log.info("userNameAttributeName={}", userNameAttributeName);

        KakaoUserInfo kakaoUserinfo = new KakaoUserInfo(attributes);
        String id = kakaoUserinfo.getSocialId();
        String name = kakaoUserinfo.getName();

        Optional<User> bySocialId = userRepository.findById(id);
        User member = bySocialId.orElseGet(() -> saveSocialUser(id, name, "kakao"));

        /* 아래 코드는 임시로 작성한 것으로 수정이 필요하다. */
        return new  PrincipalDetail();

    }
    public User saveSocialUser(String id, String name, String platform) {
        User newUser = User.builder().id(id).name(name).platform(platform).build();
        return userRepository.save(newUser);
    }
}

