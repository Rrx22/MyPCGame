package nl.rrx.event;

import nl.rrx.event.types.DamagePit;
import nl.rrx.event.types.HealingPool;
import nl.rrx.event.types.Teleport;
import nl.rrx.sprite.Direction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_SIZE;

public class EventManager {

    private final Event[] events;

    public EventManager() {
        events = new Event[]{
                new DamagePit(32, 30, Direction.RIGHT),
                new DamagePit(29, 37, Direction.DOWN),
                new HealingPool(28, 16, Direction.UP),
                new Teleport(14, 15, 3, 5),
                new Teleport(3, 6, 14, 14),
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

    public boolean checkIfEnterWillTriggerAnEvent() {
        return Arrays.stream(events)
                .filter(e -> e.isTriggeredByEnter)
                .anyMatch(COLLISION_UTIL::checkEvent);
    }
}
