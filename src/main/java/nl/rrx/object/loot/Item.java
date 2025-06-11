package nl.rrx.object.loot;


import java.awt.image.BufferedImage;

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
}
