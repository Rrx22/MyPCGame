package nl.rrx.object.loot.otherItems;

import nl.rrx.object.loot.Item;

import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.STASH;

public class OBJ_ManaPotion extends Item implements Consumable {
    public OBJ_ManaPotion(BufferedImage image, String title, String description) {
        super(image, title, description);
    }

    @Override
    public void consume() {
        // todo recover mana
        STASH.remove(this);
    }
}
