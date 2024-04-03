package org.example.awstts;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class AwsttsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsttsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            PollyService pollyService = new PollyService();
            InputStream speechStream = pollyService.synthesize(PollyService.SAMPLE, OutputFormat.Mp3);
            PollyPlayer player = new PollyPlayer();
            player.play(speechStream);
        };
    }
}

class PollyService {
    public static final String SAMPLE = "안녕하세요 저는 정준구에요 저는 여자고 박운섭을 좋아해요 ";

    private final AmazonPolly polly;
    private final Voice voice;

    public PollyService() {
        polly = AmazonPollyClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withClientConfiguration(new ClientConfiguration())
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();

        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        List<Voice> voices = describeVoicesResult.getVoices();
        Optional<Voice> neuralVoice = voices.stream()
                .filter(v -> v.getId().equals("Seoyeon"))
                .findFirst();

        if (neuralVoice.isPresent()) {
            voice = neuralVoice.get();
        } else {
            throw new RuntimeException("No neural engine supported voice found");
        }
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest()
                        .withText(text)
                        .withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
        return synthRes.getAudioStream();
    }

}

class PollyPlayer {
    public void play(InputStream speechStream) throws Exception {
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
                System.out.println(PollyService.SAMPLE);
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });
        player.play();
    }
}
