package nl.rrx.sound;

import com.adonax.audiocue.AudioCue;
import nl.rrx.config.settings.DebugSettings;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public class SoundHandler {

    private final AudioCue backgroundMusic;
    private final Map<SoundEffect, AudioCue> soundEffectMap = new EnumMap<>(SoundEffect.class);

    public SoundHandler() {
        backgroundMusic = getAudioCue("/sounds/music/adventure.wav");
        for (var soundEffect : SoundEffect.values()) {
            soundEffectMap.put(soundEffect, getAudioCue(soundEffect.uri));
        }
    }

    public void playMusic() {
        if (DebugSettings.NO_MUSIC) return;
        int instanceID = backgroundMusic.obtainInstance();
        backgroundMusic.setLooping(instanceID, -1);
        backgroundMusic.setVolume(instanceID, 0.6);
        backgroundMusic.start(instanceID);
    }

    public void playSoundEffect(SoundEffect soundEffect) {
        soundEffectMap.get(soundEffect).play(soundEffect.volume);
    }

    private AudioCue getAudioCue(String uri) {
        try {
            URL url = getClass().getResource(uri);
            AudioCue soundEffect = AudioCue.makeStereoCue(url, 4);
            soundEffect.open();
            return soundEffect;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
