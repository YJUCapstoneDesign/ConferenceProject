package org.example.mindmap.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    /**
     * Cross-Origin Resource Sharing(CORS)를 설정합니다.
     * @param registry CorsRegistry 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9000")  // 허용할 오리진(도메인) 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")  // 허용할 HTTP 메서드 설정
                .allowCredentials(true);  // 인증 정보를 허용할지 여부 설정 (예: 쿠키, HTTP 기본 인증)
    }
}