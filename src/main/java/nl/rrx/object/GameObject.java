package nl.rrx.object;

import nl.rrx.common.DrawOrderMatters;
import nl.rrx.util.PerformanceUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public class GameObject implements DrawOrderMatters {

    public final GameObjectType type;
    private final BufferedImage image;
    private final int worldX;
    private final int worldY;
    public final Rectangle collisionArea;

    public GameObject(GameObjectType type, int worldX, int worldY) {
        this(type, worldX, worldY, 0, 0);
    }

    /**
     * @param offsetX will widen the object (including the collision area)
     * @param offsetY will make the object taller (NOT including the collision area)
     */
    public GameObject(GameObjectType type, int worldX, int worldY, int offsetX, int offsetY) {
        int offsetXHalf = (offsetX == 0) ? 0 : offsetX / 2;
        this.type = type;
        this.worldX = worldX * TILE_SIZE - offsetXHalf;
        this.worldY = worldY * TILE_SIZE + offsetY;
        collisionArea = new Rectangle(this.worldX, this.worldY + offsetY, TILE_SIZE + offsetX, TILE_SIZE);
        image = PerformanceUtil.getScaledImage(type.imageUri, TILE_SIZE + offsetX, TILE_SIZE + offsetY);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isWithinScreenBoundary(PLAYER, worldX, worldY)) {
            int screenX = worldX - PLAYER.getWorldX() + PLAYER.getScreenX();
            int screenY = worldY - PLAYER.getWorldY() + PLAYER.getScreenY();
            g2.drawImage(image, screenX, screenY, null);
        }
    }

    @Override
    public int getWorldY() {
        return worldY;
    }
}
