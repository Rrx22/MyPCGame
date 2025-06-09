package nl.rrx.object.loot.weapon;

import nl.rrx.sprite.AttackType;

import java.awt.image.BufferedImage;

public class Sword extends Weapon {
    @Override
    public AttackType attackType() {
        return AttackType.SWORD;
    }

    Sword(BufferedImage image, int minAttack, int maxAttack, String title, String description) {
        super(image, minAttack, maxAttack, title, description);
    }
}
