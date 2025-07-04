package nl.rrx.object.loot.weapon;

import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum WeaponFactory {
    COMMON_SWORD("sword-common.png", WeaponType.SWORD, 1, 3, "Common Sword", "A rusty blade."),
    COMMON_AXE("axe-common.png", WeaponType.AXE, 0, 4, "Common Axe", "An old axe."),
    ;

    public final BufferedImage image;
    private final WeaponType weaponType;
    public final int minAttack;
    public final int maxAttack;
    private final String title;
    private final String[] description;

    WeaponFactory(String imageUri, WeaponType weaponType, int minAttack, int maxAttack, String title, String... description) {
        this.image = PerformanceUtil.getScaledImage("/images/weapon/" + imageUri, TILE_SIZE, TILE_SIZE);
        this.weaponType = weaponType;
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
        this.title = title;
        this.description = description;
    }

    public Weapon create() {
        return switch (this.weaponType) {
            case AXE -> new Axe(image, minAttack, maxAttack, title, description);
            case SWORD -> new Sword(image, minAttack, maxAttack, title, description);
            case null -> throw new RuntimeException("Weapon type cannot be null.");
        };
    }
}

enum WeaponType {
    SWORD,
    AXE
}
