package nl.rrx.event;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.event.types.DamagePit;
import nl.rrx.sprite.Direction;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;

public abstract class Event {

    public final int x;
    public final int y;
    public final Direction requiredDirection; // if null, any direction will trigger the event

    protected boolean canTouchAgain = false; // every fps event is triggered, so we need to halt it somehow
    protected int interactCount = 0;
    protected final boolean isTriggeredByEnter;

    protected Event(int x, int y, Direction requiredDirection, boolean isTriggeredByEnter) {
        this.x = x;
        this.y = y;
        this.requiredDirection = requiredDirection;
        this.isTriggeredByEnter = isTriggeredByEnter;
    }

    public void interact() {
        interactCount++;
        if (DebugSettings.ENABLED) System.out.println(getClass().getCanonicalName() +": "+ interactCount);
    }
    public boolean shouldTrigger() {
        boolean isCollisionWithPlayer = COLLISION_UTIL.checkEvent(this);
        if (!isCollisionWithPlayer) {
            canTouchAgain = false;
        }
        return isCollisionWithPlayer;
    }

    /**
     * For events that are triggered without key entry. It will just trigger over and over again each FPS reload.
     * This function won't allow triggering the event again until the player has left the event tile
     * Example: See {@link DamagePit#shouldTrigger}
     * @return true if the event is ready for triggering again
     */
    protected boolean avoidTriggerBeforeLeavingEventTile() {
        if (canTouchAgain) {
            return false;
        }
        canTouchAgain = true;
        return true;
    }
}
