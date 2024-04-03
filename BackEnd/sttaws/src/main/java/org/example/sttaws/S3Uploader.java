package org.example.sttaws;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;

public class S3Uploader {

    public static void main(String[] args) {
        String bucketName = "your-bucket-name"; // 업로드할 버킷 이름
        String filePath = "path/to/your/local/file"; // 업로드할 로컬 파일 경로
        String objectKey = "동북로49길-2.flac"; // 업로드할 파일의 객체 키 (파일 이름)

        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1) // 본인의 리전에 맞게 수정
                .build();

        try {
            // 로컬 파일을 읽어서 S3 버킷으로 업로드
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.putObject(request, RequestBody.fromFile(new File(filePath)));

            System.out.println("File uploaded successfully.");
        } catch (S3Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred while uploading file to S3: " + e.getMessage());
        } finally {
            // 클라이언트를 닫아서 리소스를 해제합니다.
            s3Client.close();
        }
    }
}
