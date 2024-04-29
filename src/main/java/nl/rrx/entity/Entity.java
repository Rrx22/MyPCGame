package nl.rrx.entity;

import nl.rrx.utils.Direction;

import java.awt.image.BufferedImage;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int speed;

    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;
    protected Direction direction;
}
