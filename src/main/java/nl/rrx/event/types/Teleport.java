package nl.rrx.event.types;

import nl.rrx.event.Event;
import nl.rrx.sound.SoundEffect;
import nl.rrx.state.GameState;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.config.DependencyManager.UI;

public class Teleport extends Event {

    private final int destinationX;
    private final int destinationY;

    public Teleport(int x, int y, int destinationX, int destinationY) {
        super(x, y, null, false);
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    @Override
    public void interact() {
        super.interact();
        STATE_HANDLER.setState(GameState.DIALOGUE);
        UI.showDialogue("Teleport!");
        SOUND_HANDLER.playSoundEffect(SoundEffect.POWERUP);
        PLAYER.teleport(destinationX, destinationY);
    }
}
