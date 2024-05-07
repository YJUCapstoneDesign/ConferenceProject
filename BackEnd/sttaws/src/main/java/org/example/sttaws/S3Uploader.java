package org.example.sttaws;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.File;

@Component
public class S3Uploader {
    public void uploadFile(byte[] audioBlob, String objectKey) {
        String bucketName = "unmute-1"; // 업로드할 버킷 이름

        S3Client s3Client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 본인의 리전에 맞게 수정
                .build();

        try {
            // 바이트 배열을 읽어서 S3 버킷으로 업로드
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(audioBlob));

            System.out.println("File uploaded successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while uploading file to S3", e);
        } finally {
            // 클라이언트를 닫아서 리소스를 해제합니다.
            s3Client.close();
        }
    }

}
