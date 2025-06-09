package nl.rrx.object.loot;


import java.awt.image.BufferedImage;

public abstract class Item {

    public final BufferedImage image;
    public final String title;
    public final String subtitle;
    public final String description;

    public Item(BufferedImage image, String title, String subtitle, String description) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
    }
    public Item(BufferedImage image, String title, String description) {
        this(image, title, "", description);
    }
}
