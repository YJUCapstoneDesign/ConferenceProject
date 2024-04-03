package team.broadcast.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.broadcast.global.jwt.filter.JwtAuthenticationProcessingFilter;
import team.broadcast.global.jwt.refresh.RefreshTokenRepository;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.domain.user.mysql.repository.UserRepository;
import team.broadcast.global.oauth2.handler.MyAuthFailureHandler;
import team.broadcast.global.oauth2.handler.MyAuthSuccessHandler;
import team.broadcast.global.login.filter.CustomJsonLoginFilter;
import team.broadcast.global.login.handler.LoginFailureHandler;
import team.broadcast.global.login.handler.LoginSuccessHandler;
import team.broadcast.global.login.service.LoginService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;
    private final JwtService jwtService;
    private final LoginService loginService;
    private final ObjectMapper objectMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
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
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public CustomJsonLoginFilter customJsonLoginFilter() {
        CustomJsonLoginFilter customJsonLoginFilter = new CustomJsonLoginFilter(objectMapper);
        customJsonLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customJsonLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return customJsonLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, refreshTokenRepository, userRepository);
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

        // 모든 접근에 대해 허용 <수정 필요>
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.anyRequest().permitAll());

        // filter 적용
        http.addFilterAfter(customJsonLoginFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonLoginFilter.class);



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