package nl.rrx.sprite;

import nl.rrx.sound.SoundEffect;

public enum AttackType {
    SWORD(SoundEffect.HIT_MONSTER, SoundEffect.SWING_WEAPON),
    MAGIC(SoundEffect.UNLOCK); // todo find a proper sound effect for this

    public final SoundEffect hitSound;
    public final SoundEffect missSound;

    AttackType(SoundEffect hitSound, SoundEffect missSound) {
        this.hitSound = hitSound;
        this.missSound = missSound;
    }

    AttackType(SoundEffect oneSound) {
        this(oneSound, oneSound);
    }
}
