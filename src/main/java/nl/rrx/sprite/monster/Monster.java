package nl.rrx.sprite.monster;

import nl.rrx.sprite.NonPlayerSprite;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;

public abstract class Monster extends NonPlayerSprite {

    private static final String MONSTER_IMG_ROOT = "/images/monster/";

    protected Monster(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
    }

    public abstract void onPlayerTouch();

    @Override
    protected void move() {
        COLLISION_UTIL.checkTile(this);
        COLLISION_UTIL.checkObject(this, false);
        COLLISION_UTIL.checkPlayer(this);
        COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs());
        COLLISION_UTIL.checkSprite(this, MONSTER_MGR.getMonsters());

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
}
