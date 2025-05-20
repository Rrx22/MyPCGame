package nl.rrx.sound;

import com.adonax.audiocue.AudioCue;
import nl.rrx.config.settings.DebugSettings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class SoundManager {

    private final AudioCue backgroundMusic;
    private final Map<SoundEffect, AudioCue> soundEffectMap = new EnumMap<>(SoundEffect.class);

    public SoundManager() {
        URL musicFile = getClass().getResource("/sounds/music/adventure.wav");
        backgroundMusic = getAudioCue(musicFile);
        for (var soundEffect : SoundEffect.values()) {
            soundEffectMap.put(soundEffect, getAudioCue(getClass().getResource(soundEffect.uri)));

        }
    }

    public void playMusic() {
        if (DebugSettings.NO_MUSIC) return;
        int instanceID = backgroundMusic.obtainInstance();
        backgroundMusic.setLooping(instanceID, -1);
        backgroundMusic.setVolume(instanceID, 0.5);
        backgroundMusic.start(instanceID);
    }

    public void playSoundEffect(SoundEffect soundEffect) {
        soundEffectMap.get(soundEffect).play(soundEffect.volume);
    }

    private AudioCue getAudioCue(URL url) {
        try {
            AudioCue soundEffect = AudioCue.makeStereoCue(url, 1);
            soundEffect.open();
            return soundEffect;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
