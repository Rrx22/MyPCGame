package nl.rrx.sprite.monster;

import nl.rrx.sprite.NonPlayerSprite;

public abstract class Monster extends NonPlayerSprite {

    private static final String MONSTER_IMG_ROOT = "/images/monster/";

    public Monster(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
    }

    @Override
    protected String getImageDir() {
        return MONSTER_IMG_ROOT;
    }
}
