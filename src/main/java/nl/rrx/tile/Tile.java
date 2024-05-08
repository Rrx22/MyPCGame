package nl.rrx.tile;

import java.awt.image.BufferedImage;

public record Tile(BufferedImage image, boolean isCollision) {
}
