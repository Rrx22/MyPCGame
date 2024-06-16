package nl.rrx.sprite;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.sprite.npc.NPC;
import nl.rrx.state.GameState;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.SpriteUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.SpriteSettings.INIT_PLAYER_HP;
import static nl.rrx.config.settings.WorldSettings.NO_NPC;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;
import static nl.rrx.sprite.Direction.*;

public class Player extends Sprite {

    private final int screenX;
    private final int screenY;

    private final SpriteUtil spriteUtil = new SpriteUtil();

    public Player(DependencyManager dm) {
        super(dm);
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

        loadPlayerImages();
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
        dm.collisionUtil.drawIfDebug(g2, Color.red, screenX, screenY, collisionArea);
    }

    private void move() {
        if (dm.keyHandler.nonePressed()) {
            spriteUtil.standStill();
        }

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
        dm.collisionUtil.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = dm.collisionUtil.checkObject(this, true);
        if (objIndex != NO_OBJECT) {
            interactWithObject(objIndex);
        }

        // CHECK NPC COLLISION
        int npcIndex = dm.collisionUtil.checkSprite(this, dm.npcManager.getNPCs());
        if (npcIndex != NO_NPC && dm.keyHandler.isEnterPressed()) {
            interactNPC(npcIndex);
        }

        // CHECK EVENT
        dm.eventHandler.checkEvent();

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }

        spriteUtil.updateSprite();
    }

    private void interactWithObject(int index) {
        var type = dm.objectManager.getTypeFor(index);
        switch (type) {
            // TODO
        }
    }

    private void interactNPC(int npcIndex) {
        NPC npc = dm.npcManager.get(npcIndex);
        npc.speak();
        dm.stateManager.setState(GameState.DIALOGUE);
    }

    private void loadPlayerImages() {
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
