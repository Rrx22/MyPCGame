package nl.rrx.sprite.monster;

import nl.rrx.sprite.NonPlayerSprite;

import java.awt.Color;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getScreenX;
import static nl.rrx.util.ScreenUtil.getScreenY;

public abstract class Monster extends NonPlayerSprite {

    private static final String MONSTER_IMG_ROOT = "/images/monster/";

    protected Monster(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        int screenX = getScreenX(worldX);
        int screenY = getScreenY(worldY);
        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(screenX - 1, screenY - 16, TILE_SIZE + 2, 12); // todo make the scalable
        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(screenX, screenY - 15, TILE_SIZE, 10);
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

    public void doDamage() {
        if (isTemporarilyInvincible) {
            return;
        }

        healthPoints -= 1;
        isTemporarilyInvincible = true;
        if (healthPoints <= 0) {
            this.isDying = true;
        }
    }
}
