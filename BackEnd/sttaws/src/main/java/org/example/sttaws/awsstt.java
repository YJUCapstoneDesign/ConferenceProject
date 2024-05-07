package org.example.sttaws;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class awsstt {
    public void stt() {


        // Transcribe 작업에 필요한 정보 설정
        String jobName = "KoreanSampleVoicetest"; // Transcribe 작업 이름
        String jobUri = "s3://unmute-1/달구벌대로627길.flac"; // 음성 파일이 저장된 S3 버킷 경로

        // Transcribe 클라이언트 생성
        TranscribeClient transcribeClient = TranscribeClient.builder()
                .region(Region.AP_NORTHEAST_2) // AWS 리전 설정 (본인의 리전에 맞게 수정)
                .build();

        // Transcribe 작업 시작을 요청하는 요청 객체 생성
        StartTranscriptionJobRequest startTranscriptionJobRequest = StartTranscriptionJobRequest.builder()
                .transcriptionJobName(jobName) // 작업 이름 설정
                .languageCode(LanguageCode.KO_KR) // 언어 코드 설정 (한국어)
                .mediaFormat(MediaFormat.FLAC) // 미디어 형식 설정 (FLAC)
                .media(Media.builder().mediaFileUri(jobUri).build()) // 음성 파일 URI 설정
                .mediaSampleRateHertz(48000) // 음성 파일 샘플링 속도 설정
                .settings(Settings.builder()
                        .showSpeakerLabels(true)  // 화자분리 기능 활성화
                        .maxSpeakerLabels(3)      // 최대 화자 수 설정
                        .build())
                .build();

        // Transcribe 작업 시작 요청
        transcribeClient.startTranscriptionJob(startTranscriptionJobRequest);

        // Transcribe 작업 상태 확인 및 대기
        while (true) {
            // Transcribe 작업 상태 확인 요청
            GetTranscriptionJobResponse response = transcribeClient.getTranscriptionJob(GetTranscriptionJobRequest.builder()
                    .transcriptionJobName(jobName)
                    .build());

            TranscriptionJob transcriptionJob = response.transcriptionJob();
            String status = transcriptionJob.transcriptionJobStatusAsString();

            // 작업이 완료되거나 실패하면 반복문 종료
            if (status.equals("COMPLETED") || status.equals("FAILED")) {
                break;
            }

            // 작업이 완료되지 않았을 때는 5초 대기
            System.out.println("Not ready yet...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Transcribe 작업 결과 확인
        GetTranscriptionJobResponse finalResponse = transcribeClient.getTranscriptionJob(GetTranscriptionJobRequest.builder()
                .transcriptionJobName(jobName)
                .build());

        TranscriptionJob transcriptionJob = finalResponse.transcriptionJob();
        if (transcriptionJob.transcriptionJobStatus() == TranscriptionJobStatus.COMPLETED) {
            // 작업이 완료되었을 때는 결과를 출력
            String transcriptFileUri = transcriptionJob.transcript().transcriptFileUri();
            String transcriptText = fetchTranscriptText(transcriptFileUri);
            System.out.println("Transcription Result: " + transcriptText);
        } else {
            // 작업이 실패했을 때는 실패 이유를 출력
            System.out.println("Transcription Failed. Failure Reason: " + transcriptionJob.failureReason());
        }
    }

    // Transcribe 결과를 가져오는 메서드
    private static String fetchTranscriptText(String transcriptFileUri) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(transcriptFileUri).openStream()))) {
            StringBuilder transcriptText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                transcriptText.append(line);
            }
            return transcriptText.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

