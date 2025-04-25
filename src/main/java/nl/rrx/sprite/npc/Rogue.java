package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.FPS;

public class Rogue extends NPC {

    private int actionLockCounter;
    private int disappearCounter;

    protected Rogue(DependencyManager dm, int startWorldX, int startWorldY) {
        super(dm, startWorldX, startWorldY);
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
        if (actionLockCounter++ > FPS) {
            actionLockCounter = 0;
            return true;
        }
        return false;
    }

    @Override
    public void speak() {
        super.speak();
        disappearCounter++;
        if (disappearCounter >= dialogues.length) {
            for (int i = 0; i < dm.npcManager.getNPCs().length; i++) {
                NPC npc = dm.npcManager.get(i);
                if (npc == this) {
                    dm.npcManager.remove(i);
                }
            }
        }
    }
}
