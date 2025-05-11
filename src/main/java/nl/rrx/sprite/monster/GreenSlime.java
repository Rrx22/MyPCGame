package nl.rrx.sprite.monster;

import nl.rrx.sprite.Direction;

import java.awt.Rectangle;

public class GreenSlime extends Monster {

    public GreenSlime(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        imageTypeAny = true;
        speed = 1;
        maxHP = 4;
        healthPoints = maxHP;
        collisionArea = new Rectangle();
        collisionArea.x = 3;
        collisionArea.y = 18;
        collisionArea.width = 42;
        collisionArea.height = 30; // todo make this scalable
        loadImages("greenslime");
    }

    @Override
    public void doAction() {
        speed = 1;
        int rndVal = RND.nextInt(100) + 1;

        switch (rndVal) {
            case int i when i < 20 -> direction = Direction.DOWN;
            case int i when i < 40 -> direction = Direction.UP;
            case int i when i < 60 -> direction = Direction.LEFT;
            case int i when i < 80 -> direction = Direction.RIGHT;
            default -> {
                speed = 0;
                spriteUtil.standStill();
            }
        }
    }
}
