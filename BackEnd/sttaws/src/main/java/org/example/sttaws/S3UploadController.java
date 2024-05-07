package org.example.sttaws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class S3UploadController {

    private final S3Uploader s3Uploader;
    private final AtomicInteger fileNumber = new AtomicInteger(1); // 파일 번호 추적 변수

    public S3UploadController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody byte[] audioBlob) {
        String objectKey = generateUniqueFileName(); // 고유한 파일 이름 생성

        try {
            s3Uploader.uploadFile(audioBlob, objectKey);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading file: " + e.getMessage());
        }
    }

    private String generateUniqueFileName() {
        return "recorded_audio" + fileNumber.getAndIncrement() + ".wav"; // 파일 번호 증가 후 파일 이름 생성
    }
}
