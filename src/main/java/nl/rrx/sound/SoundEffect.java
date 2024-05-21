package nl.rrx.sound;

public enum SoundEffect {
    FANFARE("/sounds/effects/fanfare.wav"),
    COIN("/sounds/effects/coin.wav"),
    POWERUP("/sounds/effects/powerup.wav"),
    UNLOCK("/sounds/effects/unlock.wav"),
    ;

    public final String uri;

    SoundEffect(String uri) {
        this.uri = uri;
    }
}
