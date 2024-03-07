package team.broadcast.global.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${janus.server}")
    private String baseUrl;

    /*
     * 일단 WebClient 를 빈 등록 한 다음에 추후에 수정할 수 있도록 한다.
     *  공식 문서 참조하여 만들 수 있도록 한다.
     * [공식 문서 사이트] : https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
