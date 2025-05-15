package nl.rrx.sprite.monster;

import nl.rrx.sprite.Direction;

import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.PLAYER;

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

        direction = switch (rndVal) {
            case int i when i < 25 -> Direction.DOWN;
            case int i when i < 50 -> Direction.UP;
            case int i when i < 75 -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }

    @Override
    public void attackPlayer() {
        PLAYER.doDamage(1);
    }
}
