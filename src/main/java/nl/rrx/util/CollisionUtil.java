package nl.rrx.util;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.event.Event;
import nl.rrx.object.GameObject;
import nl.rrx.object.ObjectManager;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Player;
import nl.rrx.sprite.Sprite;
import nl.rrx.sprite.npc.NPC;
import nl.rrx.tile.TileManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.config.settings.WorldSettings.NO_NPC;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;

public class CollisionUtil {

    private final TileManager tileManager;
    private final ObjectManager objectManager;

    public CollisionUtil(TileManager tileManager, ObjectManager objectManager) {
        this.tileManager = tileManager;
        this.objectManager = objectManager;
    }

    /**
     * Check if a sprite is going to hit a collision (i.e. tree, wall etc)
     * Sets sprite's collisionOn to true if a collision is met
     *
     * @param sprite player, npc etc
     */
    public void checkTile(Sprite sprite) {
        if (DebugSettings.FLY && sprite instanceof Player) return;

        int spriteLeftWorldX = sprite.getWorldX() + sprite.getCollisionArea().x;
        int spriteRightWorldX = sprite.getWorldX() + sprite.getCollisionArea().x + sprite.getCollisionArea().width;
        int spriteTopWorldY = sprite.getWorldY() + sprite.getCollisionArea().y;
        int spriteBottomWorldY = sprite.getWorldY() + sprite.getCollisionArea().y + sprite.getCollisionArea().height;

        int spriteLeftCol = spriteLeftWorldX / TILE_SIZE;
        int spriteRightCol = spriteRightWorldX / TILE_SIZE;
        int spriteTopRow = spriteTopWorldY / TILE_SIZE;
        int spriteBottomRow = spriteBottomWorldY / TILE_SIZE;

        switch (sprite.getDirection()) {
            case UP -> {
                spriteTopRow = (spriteTopWorldY - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileUpperRight = tileManager.getTileNum(spriteRightCol, spriteTopRow);
                if (tileManager.getTile(tileUpperLeft).isCollision() || tileManager.getTile(tileUpperRight).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case DOWN -> {
                spriteBottomRow = (spriteBottomWorldY + sprite.getSpeed()) / TILE_SIZE;
                int tileLowerLeft = tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                int tileLowerRight = tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                if (tileManager.getTile(tileLowerLeft).isCollision() || tileManager.getTile(tileLowerRight).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case LEFT -> {
                spriteLeftCol = (spriteLeftWorldX - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileLowerLeft = tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                if (tileManager.getTile(tileUpperLeft).isCollision() || tileManager.getTile(tileLowerLeft).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case RIGHT -> {
                spriteRightCol = (spriteRightWorldX + sprite.getSpeed()) / TILE_SIZE;
                int tileUpperRight = tileManager.getTileNum(spriteRightCol, spriteTopRow);
                int tileLowerRight = tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                if (tileManager.getTile(tileUpperRight).isCollision() || tileManager.getTile(tileLowerRight).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
        }
    }

    /**
     * Checks whether an object can will be collided/interacted with.
     * Sets sprite's collisionOn to true if a collision is met
     *
     * @param sprite   player, npc etc.
     * @param isPlayer Only players can interact with objects
     * @return the index of the collided object from the objects array. When no object was hit or !isPlayer, returns 999;
     */
    public int checkObject(Sprite sprite, boolean isPlayer) {
        GameObject[] gameObjects = objectManager.getGameObjects();

        for (int i = 0; i < gameObjects.length; i++) {
            GameObject obj = gameObjects[i];
            if (obj != null) {

                var spriteCollisionArea = getSpriteCollisionAreaInWorld(sprite);

                if (spriteCollisionArea.intersects(obj.collisionArea)) {
                    sprite.setCollisionOn(obj.type.isCollision);
                    return isPlayer
                            ? i
                            : NO_OBJECT;
                }
            }
        }
        return NO_OBJECT;
    }

    /**
     * Check whether a sprite collides with another sprite
     * Sets srcSprite's collisionOn to true if a collision is met
     *
     * @param srcSprite The sprite which is moving (player or npc)
     * @param npcs      Check this list of npcs for a collision with the source sprite
     * @return index of npc being collided with. 999 if no npc is hit
     */
    public int checkSprite(Sprite srcSprite, Sprite[] npcs) {
        for (int index = 0; index < npcs.length; index++) {
            Sprite npc = npcs[index];
            if (npc != null) {

                var spriteCollisionArea = getSpriteCollisionAreaInWorld(srcSprite);
                var npcCollisionArea = getSpriteCollisionAreaInWorld(npc);

                if (spriteCollisionArea.intersects(npcCollisionArea)) {
                    srcSprite.setCollisionOn(true);
                    return index;
                }
            }
        }
        return NO_NPC;
    }

    /**
     * Check whether the npc is colliding with the player
     * Sets npc's collisionOn to true if a collision is met
     *
     * @param npc
     * @param player
     */
    public void checkPlayer(NPC npc, Player player) {
        checkSprite(npc, new Sprite[]{player});
    }

    /**
     * Check
     *
     * @param player
     * @param event  (Nullable) If null, any direction will set off the event.
     * @return true if the player has set off the event
     */
    public boolean checkEvent(Player player, Event event) {
        int eventX = event.col() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
        int eventY = event.row() * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
        var eventRect = new Rectangle(eventX, eventY, 2, 2);
        var playerRect = getSpriteCollisionAreaInWorld(player);

        Direction requiredDirection = event.requiredDirection();
        return playerRect.intersects(eventRect)
                && (requiredDirection == null || requiredDirection.equals(player.getDirection()));
    }

    private static Rectangle getSpriteCollisionAreaInWorld(Sprite sprite) {
        var direction = sprite.getDirection();
        var spriteCollisionArea = new Rectangle(sprite.getCollisionArea());
        spriteCollisionArea.x += sprite.getWorldX() + direction.moveX(sprite.getSpeed());
        spriteCollisionArea.y += sprite.getWorldY() + direction.moveY(sprite.getSpeed());
        return spriteCollisionArea;
    }

    // DEBUG UTIL
    public void drawIfDebug(Graphics2D g2, Color color, int screenX, int screenY, Rectangle collisionArea) {
        if (!DebugSettings.SHOW_COLLISION) {
            return;
        }

        int x = screenX + collisionArea.x;
        int y = screenY + collisionArea.y;
        var oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(color);
        g2.drawRect(x, y, collisionArea.width, collisionArea.height);
        g2.setStroke(oldStroke);
    }
}
