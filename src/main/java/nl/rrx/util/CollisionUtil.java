package nl.rrx.util;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.object.GameObject;
import nl.rrx.object.ObjectManager;
import nl.rrx.sprite.Player;
import nl.rrx.sprite.Sprite;
import nl.rrx.tile.TileManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
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
     *
     * @param sprite player, npc etc
     * @return true if a collision will be hit
     */
    public boolean check(Sprite sprite) {
        if (DebugSettings.FLY) return false;

        int spriteLeftWorldX = sprite.getWorldX() + sprite.getCollisionArea().x;
        int spriteRightWorldX = sprite.getWorldX() + sprite.getCollisionArea().x + sprite.getCollisionArea().width;
        int spriteTopWorldY = sprite.getWorldY() + sprite.getCollisionArea().y;
        int spriteBottomWorldY = sprite.getWorldY() + sprite.getCollisionArea().y + sprite.getCollisionArea().height;

        int spriteLeftCol = spriteLeftWorldX / TILE_SIZE;
        int spriteRightCol = spriteRightWorldX / TILE_SIZE;
        int spriteTopRow = spriteTopWorldY / TILE_SIZE;
        int spriteBottomRow = spriteBottomWorldY / TILE_SIZE;

        return switch (sprite.getDirection()) {
            case UP -> {
                spriteTopRow = (spriteTopWorldY - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileUpperRight = tileManager.getTileNum(spriteRightCol, spriteTopRow);
                yield tileManager.getTile(tileUpperLeft).isCollision() || tileManager.getTile(tileUpperRight).isCollision();
            }
            case DOWN -> {
                spriteBottomRow = (spriteBottomWorldY + sprite.getSpeed()) / TILE_SIZE;
                int tileLowerLeft = tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                int tileLowerRight = tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                yield tileManager.getTile(tileLowerLeft).isCollision() || tileManager.getTile(tileLowerRight).isCollision();
            }
            case LEFT -> {
                spriteLeftCol = (spriteLeftWorldX - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileLowerLeft = tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                yield tileManager.getTile(tileUpperLeft).isCollision() || tileManager.getTile(tileLowerLeft).isCollision();
            }
            case RIGHT -> {
                spriteRightCol = (spriteRightWorldX + sprite.getSpeed()) / TILE_SIZE;
                int tileUpperRight = tileManager.getTileNum(spriteRightCol, spriteTopRow);
                int tileLowerRight = tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                yield tileManager.getTile(tileUpperRight).isCollision() || tileManager.getTile(tileLowerRight).isCollision();
            }
        };
    }

    /**
     * Checks whether an object can will be collided/interacted with.
     * @param sprite player, npc etc.
     * @param isPlayer Only players can interact with objects
     * @return the index of the collided object from the objects array. When no object was hit or !isPlayer, returns 999;
     */
    public int checkObject(Sprite sprite, boolean isPlayer) {
        GameObject[] gameObjects = objectManager.gameObjects;

        for (int i = 0; i < gameObjects.length; i++) {
            GameObject obj = gameObjects[i];
            if (obj != null) {

                var direction = sprite.getDirection();
                var spriteCollisionArea = new Rectangle(sprite.getCollisionArea());
                spriteCollisionArea.x += sprite.getWorldX() + direction.moveX(sprite.getSpeed());
                spriteCollisionArea.y += sprite.getWorldY() + direction.moveY(sprite.getSpeed());

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

    public void draw(Graphics2D g2, Player sprite) {
        int x = sprite.getScreenX() + sprite.getCollisionArea().x;
        int y = sprite.getScreenY() + sprite.getCollisionArea().y;
        g2.setColor(Color.RED);
        g2.fillRect(x, y, sprite.getCollisionArea().width, sprite.getCollisionArea().height);
    }
}
