package nl.rrx.object.loot;

import nl.rrx.object.WorldObject;

import java.awt.image.BufferedImage;

public class LootObject extends WorldObject {
    public final Item item;

    public LootObject(Item item, int worldX, int worldY) {
        super(worldX, worldY, 0, 0);
        this.item = item;
    }

    @Override
    protected BufferedImage image() {
        return item.image;
    }
}
