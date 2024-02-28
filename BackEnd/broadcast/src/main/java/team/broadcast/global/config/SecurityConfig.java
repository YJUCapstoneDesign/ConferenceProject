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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.broadcast.domain.jwt.JwtProvider;
import team.broadcast.domain.jwt.JwtVerifyFilter;
import team.broadcast.global.handler.MyAuthFailureHandler;
import team.broadcast.global.handler.MyAuthSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final OAuth2UserService oAuth2UserService;
  private final JwtProvider jwtProvider;
  private final MyAuthSuccessHandler oauthLoginSuccessHandler;
  private final MyAuthFailureHandler oauthLoginFailHandler;

  /*
   * Cors 관련 설정
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.setAllowCredentials(false);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(AbstractHttpConfigurer::disable); // form login 비활성화
    // cors 설정
    http.cors(httpSecurityCorsConfigurer ->
        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

    // http csrf 비활성화
    http.csrf(AbstractHttpConfigurer::disable);

    // session 정책 STATELESS (세션 사용안함)
    http.sessionManagement(httpSecuritySessionManagementConfigurer ->
        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // 모든 접근에 대해 허용
    http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
        authorizationManagerRequestMatcherRegistry.anyRequest().permitAll());

    // filter 적용
    http.addFilterBefore(new JwtVerifyFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

    /*
     * fomLogin() 메소드는 Server에서 로그인 페이지를 연결하는 것으로 React, Vue 같은 다른 클라이언트
     *   서버를 사용할 때 사용하지 않는다. -> Controller로 매핑한다.
     */

    // oauth 기반 로그인 설정
    http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
        httpSecurityOAuth2LoginConfigurer
            .loginPage("/oauth2/login")
            .successHandler(oauthLoginSuccessHandler)
            .failureHandler(oauthLoginFailHandler) // 로그인 실패시 사용하는 핸들러
            .userInfoEndpoint(userInfoEndpointConfig ->
                userInfoEndpointConfig.userService(oAuth2UserService)));

    return http.build();
  }

}