package hello.oauth2demo.global.config;

import hello.oauth2demo.domain.user.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                csrf((csrfConfig) -> csrfConfig.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/login/*", "/logout/*").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/")) // 로그아웃 성공시 홈화면으로 이동
                .oauth2Login(
                        oauth -> oauth.userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                );
        return http.build();
    }
}
