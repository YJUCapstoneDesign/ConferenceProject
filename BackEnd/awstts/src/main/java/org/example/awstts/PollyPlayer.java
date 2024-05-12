package org.example.awstts;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.InputStream;

@Component
public class PollyPlayer {

    public void play(InputStream speechStream) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(speechStream);
            Player player = new Player(bufferedInputStream);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
