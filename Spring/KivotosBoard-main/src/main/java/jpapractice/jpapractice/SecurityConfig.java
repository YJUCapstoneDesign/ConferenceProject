package jpapractice.jpapractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일임을 의미하는 애너테이션
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@EnableMethodSecurity // @PreAuthorize 어노테이션 활성화를 위해 사용함
public class SecurityConfig {

  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http)
      throws Exception {

    // Spring Security 기본 로그인 사용하지 않는 페이지 설정 (전체)
    http.authorizeHttpRequests(
        (authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/**"))
            .permitAll());
    // CSRF 토큰 활성화
    http.csrf(Customizer.withDefaults());

    // 로그인 -> 자세한 로직은 UserSecurityService.java 확인
    http.formLogin((formLogin) -> formLogin
        .loginPage("/login")
        .usernameParameter("id")
        .passwordParameter("passwd")
        .defaultSuccessUrl("/"));

    // usernameParameter 를 이용하여 UserDetailService 구현체에서 매개변수로 받을 매개변수명을 지정할 수 있다
    // passwordParameter 역시 마찬가지이다

    // 로그아웃
    http.logout((logout) -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true));
    // logoutRequestMatcher 으로 로그아웃 URL을 설정한다
    // logoutSuccessUrl 으로 로그아웃 성공 시 이동할 URL을 설정한다
    // invalidateHttpSession 으로 로그아웃시 세션이 만료되도록 한다
    return http.build();

  }

  // @Bean
  // AuthenticationManager authenticationManager(HttpSecurity http)
  // throws Exception {
  // AuthenticationManagerBuilder authenticationManagerBuilder = http
  // .getSharedObject(AuthenticationManagerBuilder.class);
  // authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

  // return authenticationManagerBuilder.build();
  // // AuthenticationManager는 스프링 시큐리티의 인증을 담당
  // }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
    // AuthenticationManager는 스프링 시큐리티의 인증을 담당
    // PasswordEncoder와 UserDetailsService 구현체가 자동으로 설정되어 동작
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
