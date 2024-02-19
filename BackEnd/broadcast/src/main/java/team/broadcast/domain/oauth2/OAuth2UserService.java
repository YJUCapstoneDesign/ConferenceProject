package team.broadcast.domain.oauth2;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.broadcast.domain.KakaoUserInfo;
import team.broadcast.domain.enums.UserRole;
import team.broadcast.domain.user.PrincipalDetail;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    Map<String, Object> attributes = oAuth2User.getAttributes();
    log.info("oauth2User={}",oAuth2User);
    log.info("attributes={}", attributes);

    // nameAttributeKey
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();
    log.info("userNameAttributeName={}", userNameAttributeName);

    KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
    String socialId = kakaoUserInfo.getSocialId();
    String name = kakaoUserInfo.getName();

    Optional<User> bySocialId = userRepository.findBySocialId();
    User user = bySocialId.orElseGet(() -> saveSocialUser(socialId, name));
    return new PrincipalDetail(user, Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue())), attributes);
  }

  public User saveSocialUser(String socialId, String name) {
    User newUser = User.builder().socialId(socialId).name(name).role(UserRole.USER).build();
    return userRepository.save(newUser);
  }
}
