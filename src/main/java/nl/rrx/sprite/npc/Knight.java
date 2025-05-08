package nl.rrx.sprite.npc;

import nl.rrx.sprite.Direction;

public class Knight extends NPC {

    protected Knight(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 1;
        actionLockInterval *= 3;
        loadImages("knight");
        setDialogues(
                "For honor and the realm!",
                "I yield not to evil, nor to fear!",
                "My blade bears the king’s justice!"
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
