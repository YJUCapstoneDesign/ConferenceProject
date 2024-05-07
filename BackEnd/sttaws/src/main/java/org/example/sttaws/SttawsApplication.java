package org.example.sttaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.*;

@SpringBootApplication
public class SttawsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SttawsApplication.class, args);
        //stt 생성
        //awsstt awsstt = new awsstt();
        //awsstt.stt();

    }
}