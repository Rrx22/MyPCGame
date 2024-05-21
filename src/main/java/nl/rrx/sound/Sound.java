package nl.rrx.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/music/music.wav");
        soundURL[1] = getClass().getResource("/sounds/effects/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/effects/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/effects/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/effects/fanfare.wav");
    }

    public void setFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
