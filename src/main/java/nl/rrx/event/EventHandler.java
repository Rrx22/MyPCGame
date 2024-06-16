package nl.rrx.event;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.SpriteSettings.PLAYER_RECT_X;
import static nl.rrx.config.settings.SpriteSettings.PLAYER_RECT_Y;

public class EventHandler {

    private final DependencyManager dm;

    public Rectangle eventRect;
    public int eventRectDefaultX;
    public int eventRectDefaultY;

    public EventHandler(DependencyManager dm) {
        // TODO this entire class needs some love
        //  - weird behaviour with coords
        //  - only works when moving (also goes for interacting with npc). Especially annoying when pressing ENTER
        this.dm = dm;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if (hit(29, 19, Direction.RIGHT)) damagePit();
        if (hit(23, 22)) healingPool();
        if (hit(22, 22)) teleport();
    }

    public boolean hit (int eventCol, int eventRow) {
        return hit(eventCol, eventRow, null);
    }

    public boolean hit(int eventCol, int eventRow, Direction requiredDirection) {
        boolean hit = false;

        dm.player.getCollisionArea().x = dm.player.getWorldX() + dm.player.getCollisionArea().x;
        dm.player.getCollisionArea().y = dm.player.getWorldY() + dm.player.getCollisionArea().y;
        eventRect.x = eventCol * TILE_SIZE + eventRect.x;
        eventRect.y = eventRow * TILE_SIZE + eventRect.y;

        if (dm.player.getCollisionArea().intersects(eventRect)) {
            if (requiredDirection == null || dm.player.getDirection() == requiredDirection) {
                hit = true;
            }
        }

        // reset values
        dm.player.getCollisionArea().x = PLAYER_RECT_X;
        dm.player.getCollisionArea().y = PLAYER_RECT_Y;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
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
        // TODO
        //  g2.setColor(Color.BLUE);
        //  g2.fillRect(drawX, drawY, drawW, drawH);
    }
}
