package nl.rrx.entity;

import nl.rrx.config.KeyHandler;
import nl.rrx.util.SpriteUtil;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import static nl.rrx.config.ScreenSettings.TILE_SIZE;
import static nl.rrx.entity.Direction.*;

public class Player extends Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private final KeyHandler keyH;
    private final SpriteUtil spriteUtil;

    public Player(KeyHandler keyH) {
        this.keyH = keyH;
        spriteUtil = new SpriteUtil();
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
        g2.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
    }

    private void move() {
        if (keyH.upPressed) {
            moveInDirection(UP);
        } else if (keyH.downPressed) {
            moveInDirection(DOWN);
        } else if (keyH.leftPressed) {
            moveInDirection(LEFT);
        } else if (keyH.rightPressed) {
            moveInDirection(RIGHT);
        }
    }

    private void moveInDirection(Direction direction) {
        this.direction = direction;
        x += direction.moveX(speed);
        y += direction.moveY(speed);
        spriteUtil.updateSprite();
    }


}
