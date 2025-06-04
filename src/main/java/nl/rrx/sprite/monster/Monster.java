package nl.rrx.sprite.monster;

import nl.rrx.sprite.NonPlayerSprite;

import java.awt.Color;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getScreenX;
import static nl.rrx.util.ScreenUtil.getScreenY;

public abstract class Monster extends NonPlayerSprite {

    private static final String MONSTER_IMG_ROOT = "/images/monster/";
    private static final int HPBAR_OFFSET = TILE_SIZE / 5;

    protected int attack;
    protected int defense;
    protected int expReward;
    private boolean showHpBar = false;
    private int showHpBarCounter = 0;

    protected Monster(int attack, int defense, int expReward, int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        this.attack = attack;
        this.defense = defense;
        this.expReward = expReward;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        drawHpBar(g2);
    }

    private void drawHpBar(Graphics2D g2) {
        if (isTemporarilyInvincible && !showHpBar) {
            showHpBar = true;
        }
        if (showHpBarCounter > 300) {
            showHpBar = false;
            showHpBarCounter = 0;
        }
        if (!showHpBar) {
            return;
        }

        showHpBarCounter++;
        int screenX = getScreenX(worldX);
        int screenY = getScreenY(worldY);

        double oneScale = (double) TILE_SIZE / maxHP;
        double hpBarValue = oneScale * healthPoints;

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(screenX - 1, screenY - (HPBAR_OFFSET + 1), TILE_SIZE + 2, HPBAR_OFFSET + 2);
        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(screenX, screenY - HPBAR_OFFSET, (int) hpBarValue, HPBAR_OFFSET);
    }

    public abstract void attackPlayer();

    @Override
    protected void move() {
        COLLISION_UTIL.checkTile(this);
        COLLISION_UTIL.checkObject(this, false);
        COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs());
        COLLISION_UTIL.checkSprite(this, MONSTER_MGR.getMonsters());
        if (COLLISION_UTIL.checkPlayer(this)) {
            attackPlayer();
        }

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }
        spriteUtil.updateSprite();
    }

    @Override
    protected String getImageDir() {
        return MONSTER_IMG_ROOT;
    }

    public void hurtMonster(int attack) {
        int damage = attack - defense;
        if (damage < 1) {
            return;
        }

        if (isTemporarilyInvincible) {
            return;
        }

        healthPoints -= damage;
        isTemporarilyInvincible = true;
        if (healthPoints <= 0) {
            this.isDying = true;
            PLAYER.gainExp(1);
        }
    }
}
