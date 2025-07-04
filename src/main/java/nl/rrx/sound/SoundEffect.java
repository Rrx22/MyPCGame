package nl.rrx.sound;

public enum SoundEffect {
    FANFARE("fanfare.wav"),
    COIN("coin.wav", 0.7f),
    POWERUP("powerup.wav", 0.7f),
    UNLOCK("unlock.wav"),
    HIT_MONSTER("hitmonster.wav"),
    RECEIVE_DMG("receivedmg.wav"),
    SWING_WEAPON("swingweapon.wav"),
    FIRE_SPELL("fire_spell.wav"),
    CURSOR("cursor.wav", 0.7f),
    // https://freesound.org/
    ;

    public final String uri;
    public final float volume;

    SoundEffect(String uri, float volume) {
        this.uri = "/sounds/effects/" + uri;
        this.volume = volume;
    }
    SoundEffect(String uri) {
        this(uri, 1f);
    }
}
