package nl.rrx.object;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum Weapon implements Stashable {
    COMMON_SWORD("sword-common.png", 1),
    ;

    public final BufferedImage image;
    public final int attack;

    Weapon(String fileName, int attack) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + fileName, TILE_SIZE, TILE_SIZE);
        this.attack = attack;
    }

    @Override
    public boolean canStash() {
        return true;
    }

    @Override
    public BufferedImage image() {
        return image;
    }
}
