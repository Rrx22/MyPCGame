package nl.rrx.util;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.event.Event;
import nl.rrx.object.WorldObject;
import nl.rrx.object.placed.PlacedObject;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Player.Player;
import nl.rrx.sprite.Sprite;
import nl.rrx.sprite.nps.NonPlayerSprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.TILE_HANDLER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_OUTLINER;
import static nl.rrx.config.settings.WorldSettings.DEFAULT_EVENT_SIZE;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;

public class CollisionUtil {

    public static final int NO_HIT = -1;

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
                int tileUpperLeft = TILE_HANDLER.getTileNum(spriteLeftCol, spriteTopRow);
                int tileUpperRight = TILE_HANDLER.getTileNum(spriteRightCol, spriteTopRow);
                if (TILE_HANDLER.getTile(tileUpperLeft).isCollision() || TILE_HANDLER.getTile(tileUpperRight).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case DOWN -> {
                spriteBottomRow = (spriteBottomWorldY + sprite.getSpeed()) / TILE_SIZE;
                int tileLowerLeft = TILE_HANDLER.getTileNum(spriteLeftCol, spriteBottomRow);
                int tileLowerRight = TILE_HANDLER.getTileNum(spriteRightCol, spriteBottomRow);
                if (TILE_HANDLER.getTile(tileLowerLeft).isCollision() || TILE_HANDLER.getTile(tileLowerRight).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case LEFT -> {
                spriteLeftCol = (spriteLeftWorldX - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = TILE_HANDLER.getTileNum(spriteLeftCol, spriteTopRow);
                int tileLowerLeft = TILE_HANDLER.getTileNum(spriteLeftCol, spriteBottomRow);
                if (TILE_HANDLER.getTile(tileUpperLeft).isCollision() || TILE_HANDLER.getTile(tileLowerLeft).isCollision()) {
                    sprite.setCollisionOn(true);
                }
            }
            case RIGHT -> {
                spriteRightCol = (spriteRightWorldX + sprite.getSpeed()) / TILE_SIZE;
                int tileUpperRight = TILE_HANDLER.getTileNum(spriteRightCol, spriteTopRow);
                int tileLowerRight = TILE_HANDLER.getTileNum(spriteRightCol, spriteBottomRow);
                if (TILE_HANDLER.getTile(tileUpperRight).isCollision() || TILE_HANDLER.getTile(tileLowerRight).isCollision()) {
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
        WorldObject[] gameObjects = OBJECT_MGR.getWorldObjects();

        for (int i = 0; i < gameObjects.length; i++) {
            WorldObject obj = gameObjects[i];
            if (obj == null) {
                continue;
            }
            if (obj.collisionArea.intersects(getSpriteCollisionAreaInWorld(sprite))) {
                boolean isCollision = obj instanceof PlacedObject po && po.isCollision;
                sprite.setCollisionOn(isCollision);
                return isPlayer
                        ? i
                        : NO_OBJECT;
            }
        }
        return NO_OBJECT;
    }

    /**
     * Check whether a sprite collides with another sprite
     * Sets srcSprite's collisionOn to true if a collision is met
     *
     * @param srcSprite    The sprite which is moving (player or npc)
     * @param otherSprites Check this list of sprite for a collision with the source sprite
     * @return index of npc being collided with. -1 if no npc is hit
     */
    public int checkSprite(Sprite srcSprite, Sprite[] otherSprites) {
        for (int i = 0; i < otherSprites.length; i++) {
            Sprite otherSprite = otherSprites[i];
            if (otherSprite == null || otherSprite.equals(srcSprite)) {
                continue;
            }
            var spriteCollisionArea = getSpriteCollisionAreaInWorld(srcSprite);
            var npcCollisionArea = getSpriteCollisionAreaInWorld(otherSprite);
            if (spriteCollisionArea.intersects(npcCollisionArea)) {
                if (isFacing(srcSprite, otherSprite)) {
                    srcSprite.setCollisionOn(true);
                }
                return i;
            }
        }
        return NO_HIT;
    }

    /**
     * Check whether the sprite is colliding with the player
     * Sets npc's collisionOn to true if a collision is met
     *
     * @param sprite non player character
     */
    public boolean checkPlayer(NonPlayerSprite sprite) {
        return checkSprite(sprite, new Sprite[]{PLAYER}) != NO_HIT;
    }

    /**
     * Check whether srcSprite is facing otherSprite.
     * For example, can only speak to an npc if player is facing it
     *
     * @return true if srcSprite faces otherSprite
     */
    public boolean isFacing(Sprite srcSprite, Sprite otherSprite) {
        var inFrontOFCollisionArea = getSpriteCollisionAreaInWorld(srcSprite);
        inFrontOFCollisionArea.x += srcSprite.getDirection().moveX(TILE_SIZE / 2);
        inFrontOFCollisionArea.y += srcSprite.getDirection().moveY(TILE_SIZE / 2);
        var npcCollisionArea = getSpriteCollisionAreaInWorld(otherSprite);
        return inFrontOFCollisionArea.intersects(npcCollisionArea);
    }

    /**
     * Check player event collision
     *
     * @return true if the player has set off the event
     */
    public boolean checkEvent(Event event) {
        int eventX = event.x * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
        int eventY = event.y * TILE_SIZE + DEFAULT_EVENT_OUTLINER;
        var eventRect = new Rectangle(eventX, eventY, DEFAULT_EVENT_SIZE, DEFAULT_EVENT_SIZE);
        var playerRect = getSpriteCollisionAreaInWorld(PLAYER);

        Direction requiredDirection = event.requiredDirection;
        return playerRect.intersects(eventRect)
                && (requiredDirection == null || requiredDirection.equals(PLAYER.getDirection()));
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
