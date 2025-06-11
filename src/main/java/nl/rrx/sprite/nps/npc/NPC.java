package nl.rrx.sprite.nps.npc;

import nl.rrx.sprite.Direction;
import nl.rrx.sprite.nps.NonPlayerSprite;

import java.util.ArrayList;
import java.util.List;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.UI;

public abstract class NPC extends NonPlayerSprite {

    private static final String NPC_IMG_ROOT = "/images/sprite/";

    protected List<String[]> dialogues = new ArrayList<>();
    protected int dialogueIndex;

    protected NPC(int startWorldX, int startWorldY) {
        super(startWorldX, startWorldY);
    }

    @Override
    protected void move() {
        COLLISION_UTIL.checkTile(this);
        COLLISION_UTIL.checkObject(this, false);
        boolean playerHit = COLLISION_UTIL.checkPlayer(this);
        COLLISION_UTIL.checkSprite(this, NPC_MGR.getNPCs());
        COLLISION_UTIL.checkSprite(this, MONSTER_MGR.getMonsters());
        if (playerHit && KEY_HANDLER.isEnterPressed() && COLLISION_UTIL.isFacing(PLAYER, this)) {
            speak();
        }

        if (speed == 0) {
            spriteUtil.standStill();
            return;
        }

        if (!collisionOn) {
            worldX += direction.moveX(speed);
            worldY += direction.moveY(speed);
        }
        spriteUtil.updateSprite();
    }

    public void speak() {
        UI.showDialogue(dialogues.get(dialogueIndex));
        dialogueIndex++;
        if (dialogueIndex > dialogues.size() - 1) {
            dialogueIndex = 0;
        }

        direction = switch (PLAYER.getDirection()) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    protected void addDialogue(String... dialogue) {
        this.dialogues.add(dialogue);
    }

    @Override
    protected String getImageDir() {
        return NPC_IMG_ROOT;
    }
}
