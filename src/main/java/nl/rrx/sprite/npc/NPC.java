package nl.rrx.sprite.npc;

import nl.rrx.sprite.Direction;
import nl.rrx.sprite.NonPlayerSprite;
import nl.rrx.state.GameState;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_MGR;
import static nl.rrx.config.DependencyManager.UI;

public abstract class NPC extends NonPlayerSprite {

    private static final String NPC_IMG_ROOT = "/images/sprite/";

    protected String[] dialogues = new String[20];
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
            STATE_MGR.setState(GameState.DIALOGUE);
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
        UI.setDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
        if (dialogueIndex > dialogues.length - 1) {
            dialogueIndex = 0;
        }

        direction = switch (PLAYER.getDirection()) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }

    protected void setDialogues(String... dialogues) {
        this.dialogues = dialogues;
    }

    @Override
    protected String getImageDir() {
        return NPC_IMG_ROOT;
    }
}
