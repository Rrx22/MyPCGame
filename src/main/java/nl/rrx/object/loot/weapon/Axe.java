package nl.rrx.object.loot.weapon;

import java.awt.image.BufferedImage;

public class Axe extends Weapon {
    Axe(BufferedImage image, int minAttack, int maxAttack, String title, String... description) {
        super(image, minAttack, maxAttack, title, description);
    }
}

