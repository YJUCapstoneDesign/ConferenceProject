package team.broadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync // 비동기 작업을 허용
@SpringBootApplication
public class BroadcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(BroadcastApplication.class, args);
    }

}
