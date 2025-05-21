package nl.rrx.event.types;

import nl.rrx.event.Event;
import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;

import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_MGR;
import static nl.rrx.config.DependencyManager.STATE_MGR;
import static nl.rrx.config.DependencyManager.UI;

public class HealingPool extends Event {

    private static final int MAX_HEALING_INTERACTIONS = 3;

    public HealingPool(int x, int y, Direction direction) {
        super(x, y, direction, true);
    }

    @Override
    public void interact() {
        super.interact();
        STATE_MGR.setState(GameState.DIALOGUE);
        if (interactCount <= MAX_HEALING_INTERACTIONS) {
            UI.setDialogue("You drank some water.\nYour life has been recovered!");
            SOUND_MGR.playSoundEffect(SoundEffect.COIN);
            PLAYER.recoverHP();
        } else {
            UI.setDialogue("This healing pool is depleted.");
        }
    }

    @Override
    public boolean shouldTrigger() {
        if (!super.shouldTrigger()) {
            return false;
        }
        return KEY_HANDLER.isEnterPressed();
    }
}
