package nl.rrx.object;

import nl.rrx.sprite.Player;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public class GameObject {

    public final GameObjectType type;
    public final BufferedImage image;
    public final int worldX;
    public final int worldY;
    public final Rectangle collisionArea;

    public GameObject(GameObjectType type, int worldX, int worldY) {
        this.type = type;
        this.worldX = worldX * TILE_SIZE;
        this.worldY = worldY * TILE_SIZE;
        collisionArea = new Rectangle(this.worldX, this.worldY, TILE_SIZE, TILE_SIZE);
        image = getImage(type);
    }

    public void draw(Graphics2D g2, Player player) {
        int screenX = worldX - player.getWorldX() + player.getScreenX();
        int screenY = worldY - player.getWorldY() + player.getScreenY();
        if (isWithinScreenBoundary(player, worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public static BufferedImage getImage(GameObjectType type) {
        try {
            return ImageIO.read(GameObject.class.getResourceAsStream(type.imageUri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
