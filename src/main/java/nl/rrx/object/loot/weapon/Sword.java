package nl.rrx.object.loot.weapon;

import java.awt.image.BufferedImage;

public class Sword extends Weapon {
    Sword(BufferedImage image, int minAttack, int maxAttack, String title, String... description) {
        super(image, minAttack, maxAttack, title, description);
    }
}
