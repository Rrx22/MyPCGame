package nl.rrx.event;

import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_MGR;
import static nl.rrx.config.DependencyManager.UI;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.event.EventType.DAMAGE_PIT;
import static nl.rrx.event.EventType.HEALING_POOL;
import static nl.rrx.event.EventType.TELEPORT;

public class EventHandler {

    private final Event[] events;


    public EventHandler() {
        events = new Event[]{
                new Event(DAMAGE_PIT, 29, 20, Direction.RIGHT),
                new Event(HEALING_POOL, 23, 22, null),
                new Event(TELEPORT, 22, 22, null),
        };
    }

    public void checkEvent() {
        for (var event : events) {
            if (event != null && COLLISION_UTIL.checkEvent(event)) {
                perform(event);
            }
        }
    }

    private void perform(Event event) {
        switch (event.type()) {
            case DAMAGE_PIT -> damagePit(event);
            case HEALING_POOL -> healingPool();
            case TELEPORT -> teleport();
        }
    }

    private void remove(Event event) {
        for (int i = 0; i < events.length; i++) {
            if (event == events[i]) {
                events[i] = null;
            }
        }
    }

    // EVENTS
    private void damagePit(Event event) {
        STATE_MGR.setState(GameState.DIALOGUE);
        UI.setDialogue("You fell into a pit!");
        PLAYER.doDamage(1);
        remove(event);
    }

    private void healingPool() {
        if (KEY_HANDLER.isEnterPressed()) {
            STATE_MGR.setState(GameState.DIALOGUE);
            UI.setDialogue("You drank some water.\nYour life has been recovered!");
            PLAYER.recoverHP();
        }
    }

    private void teleport() {
        STATE_MGR.setState(GameState.DIALOGUE);
        UI.setDialogue("Teleport!");
        PLAYER.teleport(47, 21);
    }

    public void draw(Graphics2D g2) {
        for (var event : events) {
            if (event == null) {
                continue;
            }
            int x = event.col() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int y = event.row() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int screenX = x - PLAYER.getWorldX() + PLAYER.getScreenX();
            int screenY = y - PLAYER.getWorldY() + PLAYER.getScreenY();
            COLLISION_UTIL.drawIfDebug(g2, Color.magenta, screenX, screenY, new Rectangle(0, 0, 2, 2));
        }
    }
}
