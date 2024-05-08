package nl.rrx.entity;

import java.awt.image.BufferedImage;

public abstract class Sprite {

    protected int worldX;
    protected int worldY;
    protected int speed;

    protected Direction direction;

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
}
