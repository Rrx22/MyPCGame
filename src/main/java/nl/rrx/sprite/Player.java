package nl.rrx.sprite;

import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.SpriteUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.SpriteSettings.INIT_PLAYER_HP;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;
import static nl.rrx.sprite.Direction.DOWN;
import static nl.rrx.sprite.Direction.LEFT;
import static nl.rrx.sprite.Direction.RIGHT;
import static nl.rrx.sprite.Direction.UP;

public class Player extends Sprite {

    public static final String IMG_ROOT = "/images/sprite/";
    private final int screenX;
    private final int screenY;

    private final SpriteUtil spriteUtil = new SpriteUtil();

    public Player() {
        super();
        worldX = SpriteSettings.INIT_WORLD_X;
        worldY = SpriteSettings.INIT_WORLD_Y;
        screenX = SpriteSettings.INIT_SCREEN_X;
        screenY = SpriteSettings.INIT_SCREEN_Y;

        maxHP = INIT_PLAYER_HP;
        healthPoints = maxHP;
        speed = SpriteSettings.INIT_SPEED;
        direction = SpriteSettings.INIT_DIRECTION;

        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;

        loadPlayerImages("boy");
    }

    public void update() {
        collisionOn = false;
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
        COLLISION_UTIL.drawIfDebug(g2, Color.red, screenX, screenY, collisionArea);
    }

    private void move() {
        if (KEY_HANDLER.nonePressed()) {
            spriteUtil.standStill();
        }

        if (KEY_HANDLER.isUpPressed()) {
            moveInDirection(UP);
        } else if (KEY_HANDLER.isDownPressed()) {
            moveInDirection(DOWN);
        }

        if (KEY_HANDLER.isLeftPressed()) {
            moveInDirection(LEFT);
        } else if (KEY_HANDLER.isRightPressed()) {
            moveInDirection(RIGHT);
        }
    }

    private void moveInDirection(Direction direction) {
        this.direction = direction;

        // CHECK TILE COLLISION
        COLLISION_UTIL.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = COLLISION_UTIL.checkObject(this, true);
        if (objIndex != NO_OBJECT) {
            interactWithObject(objIndex);
        }

        // CHECK NPC COLLISION
        COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs());

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }

        spriteUtil.updateSprite();
    }

    // todo should probably also move this to the GamePanel, to improve interacting with enter easier
    private void interactWithObject(int index) {
        var type = OBJECT_MGR.getTypeFor(index);
        switch (type) {
            // TODO
        }
    }

    public void loadPlayerImages(String imageTypeName) {
        up1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-up-1.png", TILE_SIZE, TILE_SIZE);
        up2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-up-2.png", TILE_SIZE, TILE_SIZE);
        down1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-down-1.png", TILE_SIZE, TILE_SIZE);
        down2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-down-2.png", TILE_SIZE, TILE_SIZE);
        left1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-left-1.png", TILE_SIZE, TILE_SIZE);
        left2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-left-2.png", TILE_SIZE, TILE_SIZE);
        right1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-right-1.png", TILE_SIZE, TILE_SIZE);
        right2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "-right-2.png", TILE_SIZE, TILE_SIZE);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void doDamage(int damage) {
        healthPoints -= damage;
    }

    public void recoverHP() {
        healthPoints = maxHP;
    }

    public void teleport(int x, int y) {
        worldX = x * TILE_SIZE;
        worldY = y * TILE_SIZE;
    }
}
