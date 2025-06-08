package nl.rrx.object;

import java.awt.image.BufferedImage;

public interface Stashable {
 boolean canStash();
 BufferedImage image();
 String title();
 String description();
}
