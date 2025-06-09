package nl.rrx.sprite;

import nl.rrx.sound.SoundEffect;

public enum AttackType {
    MELEE(SoundEffect.HIT_MONSTER, SoundEffect.SWING_WEAPON),
    MAGIC(SoundEffect.FIRE_SPELL);

    public final SoundEffect hitSound;
    public final SoundEffect missSound;

    AttackType(SoundEffect hitSound, SoundEffect missSound) {
        this.hitSound = hitSound;
        this.missSound = missSound;
    }

    AttackType(SoundEffect oneSound) {
        this(oneSound, oneSound);
    }

    public boolean isCloseRange() {
        return this.equals(MELEE)
                || this.equals(MAGIC);
    }
}
