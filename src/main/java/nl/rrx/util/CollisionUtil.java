package nl.rrx.util;

import nl.rrx.config.DependencyManager;
import nl.rrx.object.GameObject;
import nl.rrx.sprite.Sprite;

import java.awt.Rectangle;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.NO_OBJECT;

public class CollisionUtil {

    private final DependencyManager dm;

    public CollisionUtil(DependencyManager dm) {
        this.dm = dm;
    }

    /**
     * Check if a sprite is going to hit a collision (i.e. tree, wall etc)
     *
     * @param sprite player, npc etc
     * @return true if a collision will be hit
     */
    public boolean check(Sprite sprite) {
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
                int tileUpperLeft = dm.tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileUpperRight = dm.tileManager.getTileNum(spriteRightCol, spriteTopRow);
                yield dm.tileManager.getTile(tileUpperLeft).isCollision() || dm.tileManager.getTile(tileUpperRight).isCollision();
            }
            case DOWN -> {
                spriteBottomRow = (spriteBottomWorldY + sprite.getSpeed()) / TILE_SIZE;
                int tileLowerLeft = dm.tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                int tileLowerRight = dm.tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                yield dm.tileManager.getTile(tileLowerLeft).isCollision() || dm.tileManager.getTile(tileLowerRight).isCollision();
            }
            case LEFT -> {
                spriteLeftCol = (spriteLeftWorldX - sprite.getSpeed()) / TILE_SIZE;
                int tileUpperLeft = dm.tileManager.getTileNum(spriteLeftCol, spriteTopRow);
                int tileLowerLeft = dm.tileManager.getTileNum(spriteLeftCol, spriteBottomRow);
                yield dm.tileManager.getTile(tileUpperLeft).isCollision() || dm.tileManager.getTile(tileLowerLeft).isCollision();
            }
            case RIGHT -> {
                spriteRightCol = (spriteRightWorldX + sprite.getSpeed()) / TILE_SIZE;
                int tileUpperRight = dm.tileManager.getTileNum(spriteRightCol, spriteTopRow);
                int tileLowerRight = dm.tileManager.getTileNum(spriteRightCol, spriteBottomRow);
                yield dm.tileManager.getTile(tileUpperRight).isCollision() || dm.tileManager.getTile(tileLowerRight).isCollision();
            }
        };
    }

    /**
     * Checks whether an object can will be collided/interacted with.
     * @param sprite player, npc etc.
     * @param isPlayer NPCs will not interact with an object
     * @return the index of the collided object from the objects array for a player. When no object was hit or !isPlayer, returns 999;
     */
    public int checkObject(Sprite sprite, boolean isPlayer) {
        GameObject[] gameObjects = dm.objectManager.gameObjects;

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
}
