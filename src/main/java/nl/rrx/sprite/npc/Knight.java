package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.FPS;

public class Knight extends NPC {

    private int actionLockCounter;

    protected Knight(DependencyManager dm, int startWorldX, int startWorldY) {
        super(dm, startWorldX, startWorldY);
        speed = 1;
        loadImages("knight");
        setDialogues(
                "For honor and the realm!",
                "I yield not to evil, nor to fear!",
                "My blade bears the king’s justice!"
        );
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

    @Override
    public boolean isReadyForAction() {
        if (actionLockCounter++ > FPS * 2) {
            actionLockCounter = 0;
            return true;
        }
        return false;
    }
}
