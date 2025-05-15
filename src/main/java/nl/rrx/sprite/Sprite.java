package nl.rrx.sprite;

import nl.rrx.common.SortedDrawable;
import nl.rrx.util.SpriteUtil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.FPS;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public abstract class Sprite implements SortedDrawable {

    protected final SpriteUtil spriteUtil = new SpriteUtil();

    protected int worldX;
    protected int worldY;
    protected int speed;

    protected int maxHP;
    protected int healthPoints;

    protected Direction direction;
    protected Rectangle collisionArea;
    protected boolean collisionOn;

    protected boolean isTemporarilyInvincible = false;
    private int invincibleCounter = 0;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    protected abstract void move();

    protected Sprite(int startWorldX, int startWorldY) {
        direction = Direction.DOWN;
        worldX = TILE_SIZE * startWorldX;
        worldY = TILE_SIZE * startWorldY;
    }

    public void update() {
        collisionOn = false;
        handleInvincibility();
    }

    protected void handleInvincibility() {
        if (isTemporarilyInvincible && ++invincibleCounter > FPS) {
            isTemporarilyInvincible = false;
            invincibleCounter = 0;
        }
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
