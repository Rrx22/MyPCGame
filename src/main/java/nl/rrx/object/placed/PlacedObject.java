package nl.rrx.object.placed;

import nl.rrx.object.WorldObject;
import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class PlacedObject extends WorldObject {

    private final BufferedImage image;
    public final boolean isCollision;

    public PlacedObject(String imageUri, boolean isCollision, int worldX, int worldY, int offsetX, int offsetY) {
        super(worldX, worldY, offsetX, offsetY);
        this.image = PerformanceUtil.getScaledImage(imageUri, TILE_SIZE + offsetX, TILE_SIZE + offsetY);
        this.isCollision = isCollision;
    }

    @Override
    protected BufferedImage image() {
        return image;
    }

    public void interact() {
        // Default no-op
    }
}
