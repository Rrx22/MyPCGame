package nl.rrx.object.loot;


import nl.rrx.object.loot.otherItems.Consumable;
import nl.rrx.object.loot.shield.Shield;
import nl.rrx.object.loot.weapon.Sword;

import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;

public abstract class Item {

    public final BufferedImage image;
    public final String title;
    public final String[] description;

    public Item(BufferedImage image, String title, String... description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String stashTitle() {
        return title;
    }

    public void use() {
        switch (this) {
            case Sword sword -> PLAYER.equip(sword);
            case Shield shield -> PLAYER.equip(shield);
            case Consumable consumable -> consumable.consume();
            default -> {} // todo implement
        }
    }
}
