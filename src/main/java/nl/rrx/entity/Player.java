package nl.rrx.entity;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.SpriteSettings;
import nl.rrx.util.SpriteUtil;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import static nl.rrx.config.ScreenSettings.TILE_SIZE;
import static nl.rrx.entity.Direction.*;

public class Player extends Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private final int screenX;
    private final int screenY;

    private final transient DependencyManager dm;
    private final SpriteUtil spriteUtil;


    public Player(DependencyManager dm) {
        this.dm = dm;
        spriteUtil = new SpriteUtil();
        worldX = SpriteSettings.INIT_WORLD_X;
        worldY = SpriteSettings.INIT_WORLD_Y;
        screenX = SpriteSettings.INIT_SCREEN_X;
        screenY = SpriteSettings.INIT_SCREEN_Y;
        speed = SpriteSettings.INIT_SPEED;
        direction = SpriteSettings.INIT_DIRECTION;

        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;

        loadPlayerImages();
    }

    public void loadPlayerImages() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-down-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-left-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy-right-2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        move();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case UP -> spriteUtil.isNewDirection() ? up1 : up2;
            case DOWN -> spriteUtil.isNewDirection() ? down1 : down2;
            case LEFT -> spriteUtil.isNewDirection() ? left1 : left2;
            case RIGHT -> spriteUtil.isNewDirection() ? right1 : right2;
        };
        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }

    private void move() {
        if (dm.keyHandler.upPressed) {
            moveInDirection(UP);
        } else if (dm.keyHandler.downPressed) {
            moveInDirection(DOWN);
        } else if (dm.keyHandler.leftPressed) {
            moveInDirection(LEFT);
        } else if (dm.keyHandler.rightPressed) {
            moveInDirection(RIGHT);
        }
    }

    private void moveInDirection(Direction direction) {
        this.direction = direction;

        if (!dm.collisionUtil.check(this)) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }
        spriteUtil.updateSprite();
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
