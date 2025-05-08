package nl.rrx.sprite.npc;

import nl.rrx.sprite.Direction;

public class Giant extends NPC {

    protected Giant(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 0;
        actionLockInterval *= 3;
        loadImages("boy");
        setDialogues(
                "I was guardin' this bridge for…\nuh… since lunch, I think!?",
                "Oh! Hello tiny peoples. Wait..\nWhat was I here for again?",
                "I saw a dragon once! Or... \nwas it a cow with wings? Hmm."
        );
    }

    @Override
    protected void doAction() {
        int rndVal = RND.nextInt(100) + 1;

        direction = switch (rndVal) {
            case int i when i < 25 -> Direction.DOWN;
            case int i when i < 50 -> Direction.UP;
            case int i when i < 75 -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }
}
