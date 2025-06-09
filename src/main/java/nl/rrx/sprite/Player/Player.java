package nl.rrx.sprite.Player;

import nl.rrx.config.settings.SpriteSettings;
import nl.rrx.object.loot.shield.Shield;
import nl.rrx.object.loot.shield.ShieldFactory;
import nl.rrx.object.loot.weapon.Weapon;
import nl.rrx.object.loot.weapon.WeaponFactory;
import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.AttackUtil;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Sprite;
import nl.rrx.sprite.nps.monster.Monster;
import nl.rrx.ui.FloatingBattleMessages;
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
import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.DependencyManager.UI;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;
import static nl.rrx.sprite.Direction.DOWN;
import static nl.rrx.sprite.Direction.LEFT;
import static nl.rrx.sprite.Direction.RIGHT;
import static nl.rrx.sprite.Direction.UP;
import static nl.rrx.util.CollisionUtil.NO_HIT;

public class Player extends Sprite {

    public static final String IMG_ROOT = "/images/sprite/hero";
    private final int screenX;
    private final int screenY;
    private boolean isAttacking = false;

    //todo  ## LEVEL ##
    //   and some experience mechanism
    public int level = 1;
    public int exp;
    public int expUntilNextLevel = 4;
    public int skillPoints;
    public int coins = 0;
    //todo  ## ATTRIBUTES ##
    //  - strength
    //  - dexterity
    //  - defence
    //  - magic
    //  - ...
    public int strength;
    public int dexterity;
    public int magic;
    //todo  ## GEAR ##
    //  no longer select a class
    //  instead, let player gather weapons and gear with stats and different looks
    public Weapon weapon = WeaponFactory.COMMON_SWORD.create();
    public Shield shield = ShieldFactory.SHIELD_COMMON.create();

    public int getAttack() {
        return strength + weapon.attack();
    }

    public int getDefence() {
        return dexterity + shield.defence;
    }

    public Player() {
        super(SpriteSettings.INIT_WORLD_X, SpriteSettings.INIT_WORLD_Y);
        screenX = SpriteSettings.INIT_SCREEN_X;
        screenY = SpriteSettings.INIT_SCREEN_Y;

        direction = SpriteSettings.PLAYER_START_DIRECTION;
        maxHP = SpriteSettings.PLAYER_BASE_HP;
        healthPoints = maxHP;
        speed = SpriteSettings.PLAYER_BASE_SPEED;
        strength = SpriteSettings.PLAYER_BASE_STRENGTH;
        dexterity = SpriteSettings.PLAYER_BASE_DEXTERITY;
        magic = SpriteSettings.PLAYER_BASE_MAGIC;
        skillPoints = SpriteSettings.PLAYER_BASE_SKILLPOINTS;
        exp = 0;

        collisionArea = new Rectangle();
        collisionArea.x = SpriteSettings.PLAYER_RECT_X;
        collisionArea.y = SpriteSettings.PLAYER_RECT_Y;
        collisionArea.width = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;
        collisionArea.height = SpriteSettings.PLAYER_RECT_WIDTH_HEIGHT;

        loadPlayerImages();
        loadPlayerAttackImages();
        STASH.items()[0] = weapon;
        STASH.items()[1] = shield;
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
        OBJECT_MGR.interact(index);
    }

    public void loadPlayerImages() {
        up1 = PerformanceUtil.getScaledImage(IMG_ROOT + "/up-1.png", TILE_SIZE, TILE_SIZE);
        up2 = PerformanceUtil.getScaledImage(IMG_ROOT + "/up-2.png", TILE_SIZE, TILE_SIZE);
        down1 = PerformanceUtil.getScaledImage(IMG_ROOT + "/down-1.png", TILE_SIZE, TILE_SIZE);
        down2 = PerformanceUtil.getScaledImage(IMG_ROOT + "/down-2.png", TILE_SIZE, TILE_SIZE);
        left1 = PerformanceUtil.getScaledImage(IMG_ROOT + "/left-1.png", TILE_SIZE, TILE_SIZE);
        left2 = PerformanceUtil.getScaledImage(IMG_ROOT + "/left-2.png", TILE_SIZE, TILE_SIZE);
        right1 = PerformanceUtil.getScaledImage(IMG_ROOT + "/right-1.png", TILE_SIZE, TILE_SIZE);
        right2 = PerformanceUtil.getScaledImage(IMG_ROOT + "/right-2.png", TILE_SIZE, TILE_SIZE);
    }

    private void loadPlayerAttackImages() {
        attackUtil = AttackUtil.buildAndLoadImages(weapon, 24, 24, IMG_ROOT);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void hurtPlayer(int attack) {
        int damage = attack - getDefence();
        if (damage < 1) {
            damage = 1;
        } else if (damage > healthPoints) {
            // todo handle player dying here ?
            damage = healthPoints;
        }

        if (!isTemporarilyInvincible) {
            healthPoints -= damage;
            isTemporarilyInvincible = true;
            FloatingBattleMessages.add(this, String.valueOf(damage), FloatingBattleMessages.MessageType.PLAYER_DMG);
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
            monster.hurtMonster(getAttack());
        }
    }

    public void gainExp(int exp) {
        this.exp += exp;
        if (this.exp >= expUntilNextLevel) {
            level++;
            skillPoints++;
            recoverHP();
            this.exp -= expUntilNextLevel;
            this.expUntilNextLevel *= 2;
            SOUND_HANDLER.playSoundEffect(SoundEffect.POWERUP);
            UI.setDialogue("You are level " + level + " now!\nYou feel stronger!");
        }
        FloatingBattleMessages.add(this, exp + "xp", FloatingBattleMessages.MessageType.PLAYER_INFO);
    }

    public void skillMaxHp() {
        maxHP += 2;
        healthPoints = maxHP;
        skillPoints--;
    }

    public void equip(Weapon weapon) {
        boolean isDifferentAttackType = !this.weapon.attackType().equals(weapon.attackType());
        this.weapon = weapon;
        if (isDifferentAttackType) {
            loadPlayerAttackImages();
        }
    }
}
