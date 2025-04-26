package nl.rrx.event;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.event.EventType.DAMAGE_PIT;
import static nl.rrx.event.EventType.HEALING_POOL;
import static nl.rrx.event.EventType.TELEPORT;

public class EventHandler {

    private final DependencyManager dm;
    private final Event[] events;


    public EventHandler(DependencyManager dm) {
        this.dm = dm;
        events = new Event[]{
                new Event(DAMAGE_PIT, 29, 20, Direction.RIGHT),
                new Event(HEALING_POOL, 23, 22, null),
                new Event(TELEPORT, 22, 22, null),
        };
    }

    public void checkEvent() {
        // TODO only works when moving. Especially annoying when having to press ENTER
        for (var event : events) {
            if (dm.collisionUtil.checkEvent(event)) {
                perform(event);
            }
        }
    }

    private void perform(Event event) {
        switch (event.type()) {
            case DAMAGE_PIT -> damagePit();
            case HEALING_POOL -> healingPool();
            case TELEPORT -> teleport();
        }
    }

    // EVENTS
    private void damagePit() {
        dm.stateManager.setState(GameState.DIALOGUE);
        dm.ui.setDialogue("You fell into a pit!");
        dm.player.doDamage(1);
    }

    private void healingPool() {
        if (dm.keyHandler.isEnterPressed()) {
            dm.stateManager.setState(GameState.DIALOGUE);
            dm.ui.setDialogue("You drank some water.\nYour life has been recovered!");
            dm.player.recoverHP();
        }
    }

    private void teleport() {
        dm.stateManager.setState(GameState.DIALOGUE);
        dm.ui.setDialogue("Teleport!");
        dm.player.teleport(47, 21);
    }

    public void draw(Graphics2D g2) {
        for (var event : events) {
            int x = event.col() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int y = event.row() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int screenX = x - dm.player.getWorldX() + dm.player.getScreenX();
            int screenY = y - dm.player.getWorldY() + dm.player.getScreenY();
            dm.collisionUtil.drawIfDebug(g2, Color.magenta, screenX, screenY, new Rectangle(0, 0, 2, 2));
        }
    }
}
