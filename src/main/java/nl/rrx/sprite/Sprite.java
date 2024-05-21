package nl.rrx.sprite;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Sprite {

    protected int worldX;
    protected int worldY;
    protected int speed;

    protected Direction direction;
    protected Rectangle collisionArea;
    protected boolean collisionOn;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
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

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }
}
