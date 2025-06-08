package nl.rrx.object;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;
import java.util.Random;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum Weapon implements Stashable {
    COMMON_SWORD("sword-common.png", 1, 3, "Common Sword", "A rusty blade."),
    COMMON_AXE("axe-common.png", 0, 4, "Common Axe", "An old axe."),
    ;

    private static final Random RND = new Random();

    public final BufferedImage image;
    public final int minAttack;
    public final int maxAttack;
    private final String title;
    private final String description;

    Weapon(String fileName, int minAttack, int maxAttack, String title, String description) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + fileName, TILE_SIZE, TILE_SIZE);
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
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
        return String.format("%s (%d-%d)", title, minAttack, maxAttack);
    }

    @Override
    public String description() {
        return description;
    }

    public int attack() {
        return RND.nextInt(minAttack, maxAttack + 1);
    }
}
