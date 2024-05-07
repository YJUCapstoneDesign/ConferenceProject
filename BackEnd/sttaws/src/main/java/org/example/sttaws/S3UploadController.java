package org.example.sttaws;

import org.example.sttaws.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin
public class S3UploadController {

    @Autowired
    private S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestBody byte[] audioBlob) {
        String objectKey = "recorded_audio.wav"; // S3에 저장될 파일 이름

        try {// 클라이언트에서 전송된 바이트 배열을 S3에 업로드
            s3Uploader.uploadFile(audioBlob, objectKey);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading file: " + e.getMessage());
        }
    }
}

