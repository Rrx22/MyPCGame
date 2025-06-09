package nl.rrx.object.loot.weapon;

import nl.rrx.object.loot.Item;
import nl.rrx.sprite.AttackType;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Weapon extends Item {
    private static final Random RND = new Random();

    public final int minAttack;
    public final int maxAttack;

    public abstract AttackType attackType();

    Weapon(BufferedImage image, int minAttack, int maxAttack, String title, String description) {
        super(image, title, "(" + minAttack + "-" + maxAttack + ")", description);
        this.minAttack = minAttack;
        this.maxAttack = maxAttack;
    }

    public int attack() {
        return RND.nextInt(minAttack, maxAttack + 1);
    }
}
