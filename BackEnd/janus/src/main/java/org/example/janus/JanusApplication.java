package org.example.janus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JanusApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JanusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// JanusClient 실행
		JanusClient.runJanusClient();
	}
}
