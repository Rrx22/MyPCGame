package nl.rrx.object;

import nl.rrx.sprite.Player;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public class GameObject {

    public final BufferedImage image;
    public final String name;
    public final int worldX;
    public final int worldY;
    public boolean collision;

    public GameObject(GameObjectType type, int worldX, int worldY) {
        name = type.name();
        this.worldX = worldX * TILE_SIZE;
        this.worldY = worldY * TILE_SIZE;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(type.imageUri));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2, Player player) {
        int screenX = worldX - player.getWorldX() + player.getScreenX();
        int screenY = worldY - player.getWorldY() + player.getScreenY();
        if (isWithinScreenBoundary(player, worldX, worldY)) {
            g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }
}
