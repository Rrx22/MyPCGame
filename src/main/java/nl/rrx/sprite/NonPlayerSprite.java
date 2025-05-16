package nl.rrx.sprite;

import nl.rrx.config.settings.ScreenSettings;
import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.ScreenUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public abstract class NonPlayerSprite extends Sprite {

    protected static final Random RND = new Random();
    private int actionLockCounter;
    /**Multiply this value if you want to slow down the action interval*/
    protected int actionLockInterval = ScreenSettings.FPS;
    /**Set this true if you only load 2 images instead of 8 - for example GreenSlime*/
    protected boolean imageTypeAny = false;

    protected NonPlayerSprite(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        setDefaultCollisionArea();
    }

    protected abstract void doAction();
    protected abstract String getImageDir();

    @Override
    public void draw(Graphics2D g2) {
        if (ScreenUtil.isWithinScreenBoundary(PLAYER, worldX, worldY)) {
            BufferedImage image = getBufferedImage();
            int screenX = worldX - PLAYER.getWorldX() + PLAYER.getScreenX();
            int screenY = worldY - PLAYER.getWorldY() + PLAYER.getScreenY();
            if (isTemporarilyInvincible) { // transparant if invincible
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2.drawImage(image, screenX, screenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // reset transparency
            COLLISION_UTIL.drawIfDebug(g2, Color.YELLOW, screenX, screenY, collisionArea);
        }
    }

    private BufferedImage getBufferedImage() {
        if (imageTypeAny) {
            return spriteUtil.isNewDirection() ? up1 : up2;
        }
        return switch (direction) {
            case UP -> spriteUtil.isNewDirection() ? up1 : up2;
            case DOWN -> spriteUtil.isNewDirection() ? down1 : down2;
            case LEFT -> spriteUtil.isNewDirection() ? left1 : left2;
            case RIGHT -> spriteUtil.isNewDirection() ? right1 : right2;
        };
    }

    @Override
    public void update() {
        super.update();
        if (isReadyForAction()) {
            doAction();
        }
        move();
    }

    private boolean isReadyForAction() {
        if (actionLockCounter++ > actionLockInterval) {
            actionLockCounter = 0;
            return true;
        }
        return false;
    }

    private void setDefaultCollisionArea() {
        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
    }

    protected void loadImages(String identifier) {
        if (imageTypeAny) {
            up1 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-any-1.png", TILE_SIZE, TILE_SIZE);
            up2 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-any-2.png", TILE_SIZE, TILE_SIZE);
        } else {
            up1 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-up-1.png", TILE_SIZE, TILE_SIZE);
            up2 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-up-2.png", TILE_SIZE, TILE_SIZE);
            down1 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-down-1.png", TILE_SIZE, TILE_SIZE);
            down2 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-down-2.png", TILE_SIZE, TILE_SIZE);
            left1 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-left-1.png", TILE_SIZE, TILE_SIZE);
            left2 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-left-2.png", TILE_SIZE, TILE_SIZE);
            right1 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-right-1.png", TILE_SIZE, TILE_SIZE);
            right2 = PerformanceUtil.getScaledImage(getImageDir() + identifier + "-right-2.png", TILE_SIZE, TILE_SIZE);
        }
    }
}
