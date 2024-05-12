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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PollyService {
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

    public void synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest()
                        .withText(text)
                        .withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
        InputStream speechStream = synthRes.getAudioStream();
        PollyPlayer player = new PollyPlayer();
        player.play(speechStream);
    }
}
