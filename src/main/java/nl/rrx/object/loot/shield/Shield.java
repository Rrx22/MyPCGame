package nl.rrx.object.loot.shield;

import nl.rrx.object.loot.Item;

import java.awt.image.BufferedImage;

public class Shield extends Item {

    public final int defence;

    Shield(BufferedImage image, int defence, String title, String... description) {
        super(image, title, description);
        this.defence = defence;
    }

    @Override
    public String stashTitle() {
        return title + " ( " + defence + " )";
    }
}
