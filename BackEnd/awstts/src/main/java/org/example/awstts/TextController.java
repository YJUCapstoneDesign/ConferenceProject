package org.example.awstts;


import com.amazonaws.services.polly.model.OutputFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class TextController {

    private final PollyService pollyService;

    public TextController(PollyService pollyService) {
        this.pollyService = pollyService;
    }

    @PostMapping("/api")
    public void receiveText(@RequestBody String text) {
        try {
            pollyService.synthesize(text, OutputFormat.Mp3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
