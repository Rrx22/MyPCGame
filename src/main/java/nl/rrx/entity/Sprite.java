package nl.rrx.entity;

import java.awt.image.BufferedImage;

public abstract class Sprite {

    protected int x;
    protected int y;
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
}
