package team.broadcast.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import team.broadcast.domain.user.repository.UserRepository;
import team.broadcast.global.jwt.filter.CustomExceptionFilter;
import team.broadcast.global.jwt.filter.JwtAuthenticationProcessingFilter;
import team.broadcast.global.jwt.service.JwtService;
import team.broadcast.global.login.filter.CustomJsonLoginFilter;
import team.broadcast.global.login.handler.LoginFailureHandler;
import team.broadcast.global.login.handler.LoginSuccessHandler;
import team.broadcast.global.login.service.LoginService;
import team.broadcast.global.logout.service.LogoutService;
import team.broadcast.global.oauth2.handler.MyAuthFailureHandler;
import team.broadcast.global.oauth2.handler.MyAuthSuccessHandler;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final OAuth2UserService oAuth2UserService;
    private final JwtService jwtService;
    private final LoginService loginService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final MyAuthSuccessHandler oauthLoginSuccessHandler;
    private final MyAuthFailureHandler oauthLoginFailHandler;
    private final LogoutService logoutService;

    private final CustomExceptionFilter customExceptionFilter;

    // spring api documentation
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("UNMUTE API")
                .description("Project API Documentation")
                .version("1.0.0");
    }

    /*
     * Cors 관련 설정
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Authorization-refresh", "X-Requested-With", "Cache-Control", "Content-Type", "Accept"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
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
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:src/main/resources/static/");
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

        // 일부 경로 허용 나머지는 전부 인증이 필요하다.
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        // websocket
                        .requestMatchers("/app", "/swot", "/hat").permitAll()
                        // rest api
                        .requestMatchers("/images/**", "/images/profile/**",
                                "/api/find-pwd",
                                "/api/mind-map/**",
                                "/oauth2/**",
                                "/api/refresh-token", //refresh token 발급 api 허용
                                "/api/signup", "/",
                                "/v3/**", "/swagger-ui/**", "/api-docs",
                                "/favicon.ico").permitAll()
                        // other
                        .anyRequest().authenticated()); // 다른 곳에는 권한이 필요하다.

        // oauth 기반 로그인 설정
        http.oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                httpSecurityOAuth2LoginConfigurer
                        .loginPage("/oauth2/login")
                        .successHandler(oauthLoginSuccessHandler)
                        .failureHandler(oauthLoginFailHandler) // 로그인 실패시 사용하는 핸들러
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService)));

        http.logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .addLogoutHandler(logoutService)
                .logoutSuccessHandler(((request, response, authentication) -> {
                    SecurityContextHolder.clearContext();
                    // utf-8 적용
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().println("로그아웃 되었습니다.");
                })));

        // filter 적용
        http.addFilterAfter(customJsonLoginFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonLoginFilter.class);
        http.addFilterBefore(customExceptionFilter, jwtAuthenticationProcessingFilter().getClass());

        return http.build();
    }

}