package nl.rrx.event.types;

import nl.rrx.event.Event;
import nl.rrx.sound.SoundEffect;
import nl.rrx.state.GameState;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_MGR;
import static nl.rrx.config.DependencyManager.STATE_MGR;
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
        STATE_MGR.setState(GameState.DIALOGUE);
        UI.setDialogue("Teleport!");
        SOUND_MGR.playSoundEffect(SoundEffect.POWERUP);
        PLAYER.teleport(destinationX, destinationY);
    }
}
