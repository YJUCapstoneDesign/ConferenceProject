package org.example.janus;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JanusClient {

    // Janus Gateway와 통신을 위한 메서드
    public static void runJanusClient() {
        // Janus Gateway의 URL
        String janusUrl = "http://182.229.215.5:8088/janus";

        // Janus 세션 생성 요청 JSON
        String createSessionJson = "{ \"janus\": \"create\", \"transaction\": \"a1b1c1d1\" }";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디와 헤더를 가진 HttpEntity 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(createSessionJson, headers);

        // RestTemplate 생성
        RestTemplate restTemplate = new RestTemplate();

        // Janus Gateway에 POST 요청 전송
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(janusUrl, requestEntity, String.class);

        // 응답 확인
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // 성공적인 응답인 경우
            String responseBody = responseEntity.getBody();
            System.out.println("Janus Gateway 응답: " + responseBody);
            // 응답을 필요에 맞게 파싱하고 처리
        } else {
            // 실패한 경우
            System.err.println("Janus Gateway와 통신 실패. 상태 코드: " + responseEntity.getStatusCode());
        }
    }
}
