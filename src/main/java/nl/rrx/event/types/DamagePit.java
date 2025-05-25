package nl.rrx.event.types;

import nl.rrx.event.Event;
import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.config.DependencyManager.UI;

public class DamagePit extends Event {

    public DamagePit(int x, int y, Direction requiredDirection) {
        super(x, y, requiredDirection, false);
    }

    @Override
    public void interact() {
        super.interact();
        STATE_HANDLER.setState(GameState.DIALOGUE);
        UI.setDialogue("You fell into a pit!");
        SOUND_HANDLER.playSoundEffect(SoundEffect.UNLOCK);
        PLAYER.receiveDamage(1);
    }

    @Override
    public boolean shouldTrigger() {
        if (!super.shouldTrigger()) {
            return false;
        }
        return avoidTriggerBeforeLeavingEventTile();
    }
}
