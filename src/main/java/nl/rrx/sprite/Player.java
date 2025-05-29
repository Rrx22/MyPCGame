package nl.rrx.sprite;

import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.monster.Monster;
import nl.rrx.util.PerformanceUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.EVENT_MGR;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;
import static nl.rrx.config.settings.WorldSettings.SPEED_BOOST;
import static nl.rrx.sprite.Direction.DOWN;
import static nl.rrx.sprite.Direction.LEFT;
import static nl.rrx.sprite.Direction.RIGHT;
import static nl.rrx.sprite.Direction.UP;
import static nl.rrx.util.CollisionUtil.NO_HIT;

public class Player extends Sprite {

    public static final String IMG_ROOT = "/images/sprite/";
    private final int screenX;
    private final int screenY;
    private boolean isAttacking = false;

    public Player() {
        super(SpriteSettings.INIT_WORLD_X, SpriteSettings.INIT_WORLD_Y);
        screenX = SpriteSettings.INIT_SCREEN_X;
        screenY = SpriteSettings.INIT_SCREEN_Y;

        maxHP = SpriteSettings.INIT_PLAYER_HP;
        healthPoints = maxHP;
        speed = SpriteSettings.INIT_SPEED;
        direction = SpriteSettings.INIT_DIRECTION;

        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;

        loadPlayerImages("rogue");
    }

    @Override
    public void update() {
        super.update();
        move();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (isTemporarilyInvincible) { // transparant if invincible
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (isAttacking) {
            drawAttackImage(g2, spriteUtil.isNewDirection());
        } else {
            drawWalkImage(g2, spriteUtil.isNewDirection());
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // reset transparant drawing
        COLLISION_UTIL.drawIfDebug(g2, Color.red, screenX, screenY, collisionArea);
    }

    private void drawAttackImage(Graphics2D g2, boolean isNewDirection) {
        int offsetX = 0;
        int offsetY = 0;
        BufferedImage image = switch (direction) {
            case DOWN -> isNewDirection ? attackUtil.attackDown1() : attackUtil.attackDown2();
            case RIGHT -> isNewDirection ? attackUtil.attackRight1() : attackUtil.attackRight2();
            case UP -> {
                offsetY -= TILE_SIZE;
                yield isNewDirection ? attackUtil.attackUp1() : attackUtil.attackUp2();
            }
            case LEFT -> {
                offsetX -= TILE_SIZE;
                yield isNewDirection ? attackUtil.attackLeft1() : attackUtil.attackLeft2();
            }
        };
        g2.drawImage(image, screenX + offsetX, screenY + offsetY, null);
        attackUtil.drawIfDebug(g2, this, Color.red);
    }

    private void drawWalkImage(Graphics2D g2, boolean isNewDirection) {
        BufferedImage image = switch (direction) {
            case UP -> isNewDirection ? up1 : up2;
            case DOWN -> isNewDirection ? down1 : down2;
            case LEFT -> isNewDirection ? left1 : left2;
            case RIGHT -> isNewDirection ? right1 : right2;
        };
        g2.drawImage(image, screenX, screenY, null);
    }

    @Override
    protected void move() {
        if (isAttacking || KEY_HANDLER.isEnterPressed()) {
            boolean doNotAttack = EVENT_MGR.checkIfEnterWillTriggerAnEvent() || // trigger event instead
                    (COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs()) != NO_HIT); // trigger npc dialogue instead
            if (doNotAttack) {
                isAttacking = false;
            } else {
                isAttacking = spriteUtil.doAttackAnimation(this, attackUtil);
            }
            return;
        }

        if (KEY_HANDLER.noMovementKeysPressed()) {
            spriteUtil.standStill();
            return;
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

        // CHECK NPC/MONSTER COLLISION
        COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs());
        COLLISION_UTIL.checkSprite(this, MONSTER_MGR.getMonsters());

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }

        spriteUtil.updateSprite();
    }

    // should probably also move this to the GamePanel, to improve interacting with keypress easier
    //  - not yet a problem, since objects not yet interactable with keypresses
    private void interactWithObject(int index) {
        var type = OBJECT_MGR.getTypeFor(index);
        switch (type) {
            case BOOTS -> {
                speed += SPEED_BOOST;
                OBJECT_MGR.removeObject(index);
            }
        }
    }

    public void loadPlayerImages(String imageTypeName) {
        up1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName +  "/up-1.png", TILE_SIZE, TILE_SIZE);
        up2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName +  "/up-2.png", TILE_SIZE, TILE_SIZE);
        down1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/down-1.png", TILE_SIZE, TILE_SIZE);
        down2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/down-2.png", TILE_SIZE, TILE_SIZE);
        left1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/left-1.png", TILE_SIZE, TILE_SIZE);
        left2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/left-2.png", TILE_SIZE, TILE_SIZE);
        right1 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/right-1.png", TILE_SIZE, TILE_SIZE);
        right2 = PerformanceUtil.getScaledImage(IMG_ROOT + imageTypeName + "/right-2.png", TILE_SIZE, TILE_SIZE);
        loadPlayerAttackImages(imageTypeName);
    }

    private void loadPlayerAttackImages(String imageTypeName) {
        AttackType attackType = "wizard".equals(imageTypeName) ? AttackType.MAGIC : AttackType.SWORD;
        attackUtil = AttackUtil.buildAndLoadImages(attackType, 24, 24, IMG_ROOT + imageTypeName);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void receiveDamage(int damage) {
        if (!isTemporarilyInvincible) {
            healthPoints -= damage;
            isTemporarilyInvincible = true;
            SOUND_HANDLER.playSoundEffect(SoundEffect.RECEIVE_DMG);
        }
    }

    public void recoverHP() {
        healthPoints = maxHP;
    }

    public void teleport(int x, int y) {
        worldX = x * TILE_SIZE;
        worldY = y * TILE_SIZE;
    }

    public void hit(Monster monster) {
        if (isAttacking) {
            monster.doDamage();
        }
    }
}
