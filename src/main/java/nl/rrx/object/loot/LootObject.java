package nl.rrx.object.loot;

import nl.rrx.object.WorldObject;

import java.awt.image.BufferedImage;

public class LootObject extends WorldObject {
    public final Item lootItem;

    public LootObject(Item lootItem, int worldX, int worldY) {
        super(worldX, worldY, 0, 0);
        this.lootItem = lootItem;
    }

    @Override
    protected BufferedImage image() {
        return lootItem.image;
    }
}
