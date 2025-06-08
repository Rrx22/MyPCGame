package nl.rrx.object.item;

import java.awt.image.BufferedImage;

public class Shield extends Item {

    public final int defence;

    Shield(BufferedImage image, int defence, String title, String description) {
        super(image, title, "(" + defence + ")", description);
        this.defence = defence;
    }
}
