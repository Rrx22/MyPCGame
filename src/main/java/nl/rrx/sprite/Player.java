package nl.rrx.sprite;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.settings.DebugSettings;
import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.sound.SoundEffect;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.SpriteUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;
import static nl.rrx.config.settings.WorldSettings.SPEED_BOOST;
import static nl.rrx.sprite.Direction.*;

public class Player extends Sprite {

    private final int screenX;
    private final int screenY;

    private final DependencyManager dm;
    private final SpriteUtil spriteUtil = new SpriteUtil();

    private int keysInInventory;
    private boolean gameOver;


    public Player(DependencyManager dm) {
        this.dm = dm;
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
        g2.drawImage(image, screenX, screenY, null);

        if (DebugSettings.SHOW_COLLISION) {
            dm.collisionUtil.draw(g2, this);
        }
    }

    private void move() {
        if (dm.keyHandler.isUpPressed()) {
            moveInDirection(UP);
        } else if (dm.keyHandler.isDownPressed()) {
            moveInDirection(DOWN);
        }

        if (dm.keyHandler.isLeftPressed()) {
            moveInDirection(LEFT);
        } else if (dm.keyHandler.isRightPressed()) {
            moveInDirection(RIGHT);
        }
    }

    private void moveInDirection(Direction direction) {
        this.direction = direction;

        // CHECK TILE COLLISION
        collisionOn = dm.collisionUtil.check(this);

        // CHECK OBJECT COLLISION
        int objIndex = dm.collisionUtil.checkObject(this, true);
        if (objIndex != NO_OBJECT) {
            interactWithObject(objIndex);
        }

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }

        spriteUtil.updateSprite();
    }

    private void interactWithObject(int index) {
        var type = dm.objectManager.getTypeFor(index);
        switch (type) {
            case KEY -> {
                dm.soundManager.playSoundEffect(SoundEffect.COIN);
                dm.objectManager.removeObject(index);
                keysInInventory++;
                dm.ui.showMessage("You found a key!");
            }
            case DOOR -> {
                if (keysInInventory > 0) {
                    dm.soundManager.playSoundEffect(SoundEffect.UNLOCK);
                    dm.objectManager.removeObject(index);
                    keysInInventory--;
                    dm.ui.showMessage("You unlocked the door!");
                } else {
                    dm.ui.showMessage("You need a key!");
                }
            }
            case BOOTS -> {
                dm.soundManager.playSoundEffect(SoundEffect.POWERUP);
                speed += SPEED_BOOST;
                dm.objectManager.removeObject(index);
                dm.ui.showMessage("Speed up!");
            }
            case CHEST -> {
                gameOver = true;
                dm.objectManager.removeObject(index);
                dm.soundManager.stopMusic();
                dm.soundManager.playSoundEffect(SoundEffect.FANFARE);
            }
        }
    }

    protected void loadPlayerImages() {
        up1 = PerformanceUtil.getScaledImage("/images/player/boy-up-1.png", TILE_SIZE, TILE_SIZE);
        up2 = PerformanceUtil.getScaledImage("/images/player/boy-up-2.png", TILE_SIZE, TILE_SIZE);
        down1 = PerformanceUtil.getScaledImage("/images/player/boy-down-1.png", TILE_SIZE, TILE_SIZE);
        down2 = PerformanceUtil.getScaledImage("/images/player/boy-down-2.png", TILE_SIZE, TILE_SIZE);
        left1 = PerformanceUtil.getScaledImage("/images/player/boy-left-1.png", TILE_SIZE, TILE_SIZE);
        left2 = PerformanceUtil.getScaledImage("/images/player/boy-left-2.png", TILE_SIZE, TILE_SIZE);
        right1 = PerformanceUtil.getScaledImage("/images/player/boy-right-1.png", TILE_SIZE, TILE_SIZE);
        right2 = PerformanceUtil.getScaledImage("/images/player/boy-right-2.png", TILE_SIZE, TILE_SIZE);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getKeysInInventory() {
        return keysInInventory;
    }
}
