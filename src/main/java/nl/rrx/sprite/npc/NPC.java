package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.settings.DebugSettings;
import nl.rrx.sprite.Sprite;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.ScreenUtil;
import nl.rrx.util.SpriteUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import static nl.rrx.config.settings.ScreenSettings.FPS;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public abstract class NPC extends Sprite {

    // TODO refactor/cleanup this class
    private static final String NPC_IMG_ROOT = "/images/npc/";
    protected static final Random RND = new Random();

    private final SpriteUtil spriteUtil = new SpriteUtil();

    private int actionLockCounter;

    protected NPC(DependencyManager dm) {
        super(dm);
        collisionArea = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);
    }

    protected abstract void doNpcAction();

    public void update() {
        if (actionLockCounter++ > FPS * 2) {
            doNpcAction();
            actionLockCounter = 0;
        }

        collisionOn = dm.collisionUtil.check(this);
        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }
        spriteUtil.updateSprite();
    }

    public void draw(Graphics2D g2) {
        if (ScreenUtil.isWithinScreenBoundary(dm.player, worldX, worldY)) {
            BufferedImage image = switch (direction) {
                case UP -> spriteUtil.isNewDirection() ? up1 : up2;
                case DOWN -> spriteUtil.isNewDirection() ? down1 : down2;
                case LEFT -> spriteUtil.isNewDirection() ? left1 : left2;
                case RIGHT -> spriteUtil.isNewDirection() ? right1 : right2;
            };

            int screenX = worldX - dm.player.getWorldX() + dm.player.getScreenX();
            int screenY = worldY - dm.player.getWorldY() + dm.player.getScreenY();
            g2.drawImage(image, screenX, screenY, null);

            if (DebugSettings.SHOW_COLLISION) dm.collisionUtil.draw(g2, Color.YELLOW, screenX, screenY, collisionArea);
        }
    }

    protected void loadImages(String npcType) {
        up1 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-up-1.png", TILE_SIZE, TILE_SIZE);
        up2 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-up-2.png", TILE_SIZE, TILE_SIZE);
        down1 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-down-1.png", TILE_SIZE, TILE_SIZE);
        down2 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-down-2.png", TILE_SIZE, TILE_SIZE);
        left1 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-left-1.png", TILE_SIZE, TILE_SIZE);
        left2 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-left-2.png", TILE_SIZE, TILE_SIZE);
        right1 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-right-1.png", TILE_SIZE, TILE_SIZE);
        right2 = PerformanceUtil.getScaledImage(NPC_IMG_ROOT + npcType + "-right-2.png", TILE_SIZE, TILE_SIZE);

    }
}
