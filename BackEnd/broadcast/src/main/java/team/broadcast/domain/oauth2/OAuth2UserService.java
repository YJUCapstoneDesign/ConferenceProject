package team.broadcast.domain.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enums.Membership;
import team.broadcast.domain.enums.UserRole;
import team.broadcast.domain.kakao.KakaoUserInfo;
import team.broadcast.domain.user.PrincipalDetail;
import team.broadcast.domain.user.User;
import team.broadcast.domain.user.UserRepository;

import java.util.Collections;
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
    log.info("attributes={}", attributes);

    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();
    log.info("userNameAttributeName={}", userNameAttributeName);

    KakaoUserInfo kakaoUserinfo = new KakaoUserInfo(attributes);
    String id = kakaoUserinfo.getSocialId();
    String name = kakaoUserinfo.getName();

    Optional<User> bySocialId = userRepository.findById(id);
    User user = bySocialId.orElseGet(() -> saveSocialUser(id, name, "kakao"));

    /*
     * Date-2024-02-19:22:49
     * 코드 작성 수정이 필요할 수 있음.
     */
    return new PrincipalDetail(user, Collections.singleton(new SimpleGrantedAuthority(user.getAdmin().getValue())),
        attributes);

  }

  public User saveSocialUser(String id, String name, String platform) {
    User newUser = User.builder()
        .id(id)
        .name(name)
        .platform(platform)
        .admin(UserRole.USER)
        .membership(Membership.BASIC)
        .build();
    return userRepository.save(newUser);
  }
}