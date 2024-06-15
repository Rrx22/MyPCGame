package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.FPS;

public class Wizard extends NPC {

    private int actionLockCounter;

    protected Wizard(DependencyManager dm, int startWorldX, int startWorldY) {
        super(dm, startWorldX, startWorldY);
        speed = 1;
        loadImages("wizard");
        setDialogues(
                "Hello, lad.",
                "The treacherous are ever distrustful.",
                "May the wind under your wings bear\nyou where the sun sails and the\nmoon walks."
        );
    }

    @Override
    protected void doNpcAction() {
        Integer rndVal = RND.nextInt(100) + 1; // TODO upgrade to java 23 and use primitive instead (?)

        direction = switch (rndVal) {
            case Integer i when i < 25 -> Direction.DOWN;
            case Integer i when i < 50 -> Direction.UP;
            case Integer i when i < 75 -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }

    @Override
    public boolean isReadyForAction() {
        if (actionLockCounter++ > FPS * 2) {
            actionLockCounter = 0;
            return true;
        }
        return false;
    }
}
