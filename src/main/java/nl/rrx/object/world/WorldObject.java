package nl.rrx.object.world;

import nl.rrx.common.SortedDrawable;
import nl.rrx.config.settings.DebugSettings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getScreenX;
import static nl.rrx.util.ScreenUtil.getScreenY;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public abstract class WorldObject implements SortedDrawable {

    protected abstract BufferedImage image();

    private final int worldX;
    private final int worldY;
    private final int offsetX;
    private final int offsetY;
    public final Rectangle collisionArea;

    /**
     * @param offsetX will widen the object (including the collision area)
     * @param offsetY will make the object taller (NOT including the collision area)
     */
    public WorldObject(int worldX, int worldY, int offsetX, int offsetY) {
        this.offsetY = offsetY;
        this.offsetX = (offsetX == 0) ? 0 : offsetX / 2;
        this.worldX = worldX * TILE_SIZE;
        this.worldY = worldY * TILE_SIZE;
        collisionArea = new Rectangle(this.worldX - this.offsetX, this.worldY, TILE_SIZE + offsetX, TILE_SIZE);
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isWithinScreenBoundary(PLAYER, worldX, worldY)) {
            int screenX = getScreenX(worldX) - offsetX;
            int screenY = getScreenY(worldY) - offsetY;
            g2.drawImage(image(), screenX, screenY, null);
            if (DebugSettings.SHOW_COLLISION) drawCollision(g2, screenX, screenY);
        }
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    private void drawCollision(Graphics2D g2, int screenX, int screenY) {
        var oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.CYAN);
        g2.drawRect(screenX, screenY + offsetY, TILE_SIZE + offsetX*2, TILE_SIZE);
        g2.setStroke(oldStroke);
    }
}
