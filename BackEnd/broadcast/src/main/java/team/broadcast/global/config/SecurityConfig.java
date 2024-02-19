package team.broadcast.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final OAuth2UserService oAuth2UserService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CommonLoginSuccessHandler commonLoginSuccessHandler() {
    return new CommonLoginSuccessHandler();
  }

  @Bean
  public CommonLoginFailHandler commonLoginFailHandler() {
    return new CommonLoginFailHandler();
  }
  @Bean
  public JwtVerifyFilter jwtVerifyFilter() {
    return new JwtVerifyFilter();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    // 보안을 위해 csrf 방지
    http.csrf(AbstractHttpConfigurer::disable);

    // STATELESS 설정 세션을 사용하지 않도록 한다.
    http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
      httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    // 모든 링크에 대해 허용 (테스트를 위해 임시로 설정)
    http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
        authorizationManagerRequestMatcherRegistry.anyRequest().permitAll());

    // 다음 필터가 적용되기 전에 먼저 아래 필터가 적용이 된다.
    http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

    // 일반 로그인 페이지에 대한 설정
    http.formLogin(httpSecurityFormLoginConfigurer -> {
      httpSecurityFormLoginConfigurer
          .loginPage("/login")
          .successHandler(commonLoginSuccessHandler())
          .failureHandler(commonLoginFailHandler());
    });

    http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
      httpSecurityOAuth2LoginConfigurer.loginPage("/oauth/login")
          .successHandler(commonLoginSuccessHandler())
          .userInfoEndpoint(userInfoEndpointConfig -> {
            userInfoEndpointConfig.userService(oAuth2UserService);
          });
    });


    return http.build();
  }
}
