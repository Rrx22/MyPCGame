package nl.rrx.entity;

import java.awt.image.BufferedImage;

public abstract class Sprite {

    protected int x = 100;
    protected int y = 100;
    protected int speed = 4;

    protected Direction direction = Direction.DOWN;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;
}
