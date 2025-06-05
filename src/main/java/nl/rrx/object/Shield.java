package nl.rrx.object;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum Shield implements Stashable {
    COMMON("shield-common.png", 1),
    ;


    public final BufferedImage image;
    public final int defence;

    Shield(String fileName, int defence) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + fileName, TILE_SIZE, TILE_SIZE);
        this.defence = defence;
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
