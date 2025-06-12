package nl.rrx.object.loot.shield;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum ShieldFactory {
    SHIELD_COMMON("shield-wood.png", 1, "Common Shield", "A worn out shield."),
    SHIELD_UNCOMMON("shield-blue.png", 2, "Uncommon Shield", "This shield was painted", "blue."),
    ;

    public final BufferedImage image;
    public final int defence;
    private final String title;
    private final String[] description;

    ShieldFactory(String imageUri, int defence, String title, String... description) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + imageUri, TILE_SIZE, TILE_SIZE);
        this.defence = defence;
        this.title = title;
        this.description = description;
    }

    public Shield create() {
        return new Shield(image, defence, title, description);
    }
}
