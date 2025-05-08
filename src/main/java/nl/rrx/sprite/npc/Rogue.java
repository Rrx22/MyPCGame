package nl.rrx.sprite.npc;

import nl.rrx.sprite.Direction;

import static nl.rrx.config.DependencyManager.NPC_MGR;

public class Rogue extends NPC {

    private int disappearCounter;

    protected Rogue(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 2;
        loadImages("rogue");
        setDialogues(
                "What d'you want from me?",
                "Leave me alone...",
                "Seriously, you're drawing too much attention!",
                "Gotta go. See you around, kid."
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

    @Override
    public void speak() {
        super.speak();
        disappearCounter++;
        if (disappearCounter >= dialogues.length) {
            for (int i = 0; i < NPC_MGR.getNPCs().length; i++) {
                NPC npc = NPC_MGR.get(i);
                if (npc == this) {
                    NPC_MGR.remove(i);
                }
            }
        }
    }
}
