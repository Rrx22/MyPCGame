package nl.rrx.sound;

import nl.rrx.config.settings.DebugSettings;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class SoundManager {

    private final Clip backgroundMusic;
    private final Map<SoundEffect, URL> soundEffects = new EnumMap<>(SoundEffect.class);

    public SoundManager() {
        URL musicFile = getClass().getResource("/sounds/music/adventure.wav");
        backgroundMusic = getClip(musicFile);

        for (var soundEffect : SoundEffect.values()) {
            soundEffects.put(soundEffect, getClass().getResource(soundEffect.uri));
        }
    }

    public void playMusic() {
        if (DebugSettings.NO_MUSIC) return;
        backgroundMusic.start();
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        if (DebugSettings.NO_MUSIC) return;
        backgroundMusic.stop();
    }

    public void playSoundEffect(SoundEffect key) {
        Clip soundEffect = getClip(soundEffects.get(key));
        soundEffect.start();
    }

    public Clip getClip(URL url) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
