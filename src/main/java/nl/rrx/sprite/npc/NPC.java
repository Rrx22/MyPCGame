package nl.rrx.sprite.npc;

import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Sprite;
import nl.rrx.state.GameState;
import nl.rrx.util.PerformanceUtil;
import nl.rrx.util.ScreenUtil;
import nl.rrx.util.SpriteUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_MGR;
import static nl.rrx.config.DependencyManager.UI;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public abstract class NPC extends Sprite {

    private static final String NPC_IMG_ROOT = "/images/sprite/";
    protected static final Random RND = new Random();

    private final SpriteUtil spriteUtil = new SpriteUtil();

    protected String[] dialogues = new String[20];
    protected int dialogueIndex;

    protected NPC(int startWorldX, int startWorldY) {
        super();
        worldX = TILE_SIZE * startWorldX;
        worldY = TILE_SIZE * startWorldY;
        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
    }

    protected abstract void doNpcAction();
    protected abstract boolean isReadyForAction();

    public void update() {
        collisionOn = false;
        if (isReadyForAction()) {
            doNpcAction();
        }
        move();
    }

    private void move() {
        COLLISION_UTIL.checkTile(this);
        COLLISION_UTIL.checkObject(this, false);
        boolean playerHit = COLLISION_UTIL.checkPlayer(this);
        if (playerHit && KEY_HANDLER.isEnterPressed() && COLLISION_UTIL.isFacing(PLAYER, this)) {
            speak();
            STATE_MGR.setState(GameState.DIALOGUE);
        }

        if (speed == 0) {
            return;
        }

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }
        spriteUtil.updateSprite();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (ScreenUtil.isWithinScreenBoundary(PLAYER, worldX, worldY)) {
            BufferedImage image = switch (direction) {
                case UP -> spriteUtil.isNewDirection() ? up1 : up2;
                case DOWN -> spriteUtil.isNewDirection() ? down1 : down2;
                case LEFT -> spriteUtil.isNewDirection() ? left1 : left2;
                case RIGHT -> spriteUtil.isNewDirection() ? right1 : right2;
            };

            int screenX = worldX - PLAYER.getWorldX() + PLAYER.getScreenX();
            int screenY = worldY - PLAYER.getWorldY() + PLAYER.getScreenY();
            g2.drawImage(image, screenX, screenY, null);

            COLLISION_UTIL.drawIfDebug(g2, Color.YELLOW, screenX, screenY, collisionArea);
        }
    }

    public void speak() {
        UI.setDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
        if (dialogueIndex > dialogues.length - 1) {
            dialogueIndex = 0;
        }

        direction = switch (PLAYER.getDirection()) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
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

    protected void setDialogues(String... dialogues) {
        this.dialogues = dialogues;
    }
}
