package nl.rrx.sprite.nps.npc;

import nl.rrx.sprite.Direction;

public class Knight extends NPC {

    protected Knight(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 1;
        slowerActionInterval(3);
        loadImages("knight");
        addDialogue("For honor and the realm!");
        addDialogue("I yield not to evil, nor to fear!");
        addDialogue("My blade bears the king’s justice!");
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
