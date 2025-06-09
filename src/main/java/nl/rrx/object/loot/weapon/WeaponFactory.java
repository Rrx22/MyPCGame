package nl.rrx.object.loot.weapon;

import nl.rrx.sprite.AttackType;
import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.sprite.AttackType.AXE;
import static nl.rrx.sprite.AttackType.SWORD;

public enum WeaponFactory {
    COMMON_SWORD(SWORD, "sword-common.png", 1, 3, "Common Sword", "A rusty blade."),
    COMMON_AXE(AXE, "axe-common.png", 0, 4, "Common Axe", "An old axe."),
    ;

    public final BufferedImage image;
    public final int minAttack;
    public final int maxAttack;
    private final String title;
    private final String description;
    private final AttackType type;

    WeaponFactory(AttackType type, String imageUri, int minAttack, int maxAttack, String title, String description) {
        this.type = type;
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + imageUri, TILE_SIZE, TILE_SIZE);
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.title = title;
        this.description = description;
    }

    public Weapon create() {
        return switch (type) {
            case AXE -> new Axe(image, minAttack, maxAttack, title, description);
            case SWORD -> new Sword(image, minAttack, maxAttack, title, description);
            case MAGIC -> null; // todo
        };
    }
}
