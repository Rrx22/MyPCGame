package nl.rrx.object.loot.weapon;

import nl.rrx.sprite.AttackType;

import java.awt.image.BufferedImage;

public class Axe extends Weapon{
    @Override
    public AttackType attackType() {
        return AttackType.AXE;
    }

    Axe(BufferedImage image, int minAttack, int maxAttack, String title, String description) {
        super(image, minAttack, maxAttack, title, description);
    }
}

