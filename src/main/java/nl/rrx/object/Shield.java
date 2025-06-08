package nl.rrx.object;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum Shield implements Stashable {
    COMMON("shield-common.png", 1, "Common Shield", "A worn out shield."),
    ;

    public final BufferedImage image;
    public final int defence;
    private final String title;
    private final String description;

    Shield(String fileName, int defence, String title, String description) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + fileName, TILE_SIZE, TILE_SIZE);
        this.defence = defence;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean canStash() {
        return true;
    }

    @Override
    public BufferedImage image() {
        return image;
    }

    @Override
    public String title() {
        return String.format("%s (%d)", title, defence);
    }

    @Override
    public String description() {
        return description;
    }
}
