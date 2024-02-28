package team.broadcast.domain.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.broadcast.domain.enums.SocialType;
import team.broadcast.domain.user.User;
import team.broadcast.domain.user.UserRepository;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private static final String KAKAO = "kakao";
  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    /*
    DefaultOAuth2UserService 객체를 생성해서 OAuthUser에 정보를 가져 온다.
     */
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    SocialType socialType = getSocialType(registrationId);
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    // 소셜 타입에 따른 OAuthAttributes 객체 생성
    OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
    User createdUser = getUser(extractAttributes, socialType);

    return new CustomOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(createdUser.getAdmin().getValue())),
        attributes,
        extractAttributes.getNameAttributeKey(),
        createdUser.getEmail(),
        createdUser.getAdmin()
    );
  }

  private SocialType getSocialType(String registrationId) {
    if (KAKAO.equals(registrationId)) {
      return SocialType.KAKAO;
    }
    return SocialType.GOOGLE;
  }

  private User getUser(OAuthAttributes attributes, SocialType socialType) {
    User findUser = userRepository.findById(
        attributes.getOAuthUserInfo().getId()).orElse(null);

    if (findUser == null) {
      return saveUser(attributes, socialType);
    }
    return findUser;
  }

  // 여기서 Repository에 맞게 바꾼다.
  private User saveUser(OAuthAttributes attributes, SocialType socialType) {
    User createdUser = attributes.toEntity(socialType, attributes.getOAuthUserInfo());
    return userRepository.save(createdUser);
  }
}