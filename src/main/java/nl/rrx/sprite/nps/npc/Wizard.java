package nl.rrx.sprite.nps.npc;

import nl.rrx.sprite.Direction;

public class Wizard extends NPC {

    protected Wizard(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 1;
        slowerActionInterval(2);
        loadImages("wizard");
        addDialogue("Hello, lad.");
        addDialogue("The treacherous are ever distrustful.");
        addDialogue("May the wind under your wings bear you", "where the sun sails and the moon walks.");
    }

    @Override
    protected void doAction() {
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
