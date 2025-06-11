package nl.rrx.sprite.nps.npc;

import nl.rrx.sprite.Direction;

public class Giant extends NPC {

    protected Giant(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 0;
        direction = Direction.RIGHT;
        slowerActionInterval(3);
        loadImages("giant");
        addDialogue("I was guardin' this bridge for…", "uh… since lunch, I think!?");
        addDialogue("Oh! Hello tiny peoples. Wait..", "What was I here for again?");
        addDialogue("I saw a dragon once! Or... ", "was it a cow with wings? Hmm.");
    }

    @Override
    protected void doAction() {
        direction = switch (RND.nextInt(100) + 1) {
            case int i when i < 25 -> Direction.DOWN;
            case int i when i < 50 -> Direction.UP;
            case int i when i < 75 -> Direction.LEFT;
            default -> Direction.RIGHT;
        };
    }
}
