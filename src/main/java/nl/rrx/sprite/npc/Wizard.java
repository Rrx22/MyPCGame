package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class Wizard extends NPC {

    protected Wizard(DependencyManager dm, int startWorldX, int startWorldY) {
        super(dm);
        direction = Direction.DOWN;
        speed = 1;
        worldX = TILE_SIZE * startWorldX;
        worldY = TILE_SIZE * startWorldY;

        loadImages("wizard");
    }

    @Override
    protected void doNpcAction() {
        Integer rndVal = RND.nextInt(100) + 1;

        direction = switch (rndVal) {
            case Integer i when i < 25 -> Direction.DOWN;
            case Integer i when i < 50 -> Direction.UP;
            case Integer i when i < 75 -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }
}
