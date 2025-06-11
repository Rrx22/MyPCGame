package nl.rrx.sprite.nps.npc;

import nl.rrx.sprite.Direction;

import static nl.rrx.config.DependencyManager.NPC_MGR;

public class Rogue extends NPC {

    private int disappearCounter;

    protected Rogue(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
        speed = 2;
        loadImages("rogue");
        addDialogue("What d'you want from me?");
        addDialogue("Leave me alone...");
        addDialogue("Seriously, you're drawing too much attention!");
        addDialogue("Gotta go. See you around, kid.");
    }

    @Override
    protected void doAction() {
        speed = 2;
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

    @Override
    public void speak() {
        super.speak();
        disappearCounter++;
        if (disappearCounter >= dialogues.size()) {
            for (int i = 0; i < NPC_MGR.getNPCs().length; i++) {
                NPC npc = NPC_MGR.get(i);
                if (npc == this) {
                    NPC_MGR.remove(i);
                }
            }
        }
    }
}
