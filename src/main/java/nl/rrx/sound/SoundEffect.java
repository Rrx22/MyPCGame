package nl.rrx.sound;

public enum SoundEffect {
    FANFARE("fanfare.wav"),
    COIN("coin.wav"),
    POWERUP("powerup.wav"),
    UNLOCK("unlock.wav"),
    HIT_MONSTER("hitmonster.wav"),
    RECEIVE_DMG("receivedmg.wav"),
    SWING_WEAPON("swingweapon.wav"),
    // https://freesound.org/
    ;

    public final String uri;

    SoundEffect(String uri) {
        this.uri = "/sounds/effects/" + uri;
    }
}
