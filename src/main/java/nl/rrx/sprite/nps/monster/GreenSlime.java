package nl.rrx.sprite.nps.monster;

import nl.rrx.sprite.Direction;

import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.PLAYER;

public class GreenSlime extends Monster {

    public int baseMaxHP() { return 3; }
    public int baseAttack() { return 3; }
    public int baseDefense() { return 0; }
    public int baseExp() { return 0; }

    public GreenSlime(int level, int startWorldX, int startWorldY) {
        super(level, startWorldX, startWorldY);
        slowerActionInterval(2);
        imageTypeAny = true;
        speed = 1;
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
        PLAYER.hurtPlayer(attack);
    }

    @Override
    public void hurtMonster(int attack) {
        super.hurtMonster(attack);
        runAwayFromPlayer();
    }

    private void runAwayFromPlayer() {
        resetActionLockCounter();
        this.direction = PLAYER.getDirection();
    }
}
