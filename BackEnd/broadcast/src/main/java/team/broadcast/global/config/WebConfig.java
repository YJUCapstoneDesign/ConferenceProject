package team.broadcast.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebConfig {

  // WebClient 코드 사용을 위한 빈 등록
  @Bean
  public WebClient webClient() {
    return WebClient.builder().build();
  }
}
