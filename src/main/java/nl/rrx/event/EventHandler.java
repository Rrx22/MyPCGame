package nl.rrx.event;

import nl.rrx.event.types.DamagePit;
import nl.rrx.event.types.HealingPool;
import nl.rrx.event.types.Teleport;
import nl.rrx.sprite.Direction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_SIZE;

public class EventHandler {

    private final Event[] events;

    public EventHandler() {
        events = new Event[]{
                new DamagePit(29, 20, Direction.RIGHT),
                new HealingPool(23, 22),
                new Teleport(22, 22, 47, 21),
        };
    }

    public void checkEvent() {
        for (var event : events) {
            if (event != null && event.shouldTrigger()) {
                event.interact();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (var event : events) {
            if (event == null) {
                continue;
            }
            int x = event.x * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int y = event.y * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
            int screenX = x - PLAYER.getWorldX() + PLAYER.getScreenX();
            int screenY = y - PLAYER.getWorldY() + PLAYER.getScreenY();
            COLLISION_UTIL.drawIfDebug(g2, Color.magenta, screenX, screenY, new Rectangle(0, 0, DEFAULT_EVENT_SIZE, DEFAULT_EVENT_SIZE));
        }
    }
}
