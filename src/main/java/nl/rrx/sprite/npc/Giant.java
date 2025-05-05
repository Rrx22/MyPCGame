package nl.rrx.sprite.npc;

import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.FPS;

public class Giant extends NPC {

    private int actionLockCounter;

    protected Giant(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 0;
        loadImages("boy");
        setDialogues(
                "I was guardin' this bridge for…\nuh… since lunch, I think!?",
                "Oh! Hello tiny peoples. Wait..\nWhat was I here for again?",
                "I saw a dragon once! Or... \nwas it a cow with wings? Hmm."
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
