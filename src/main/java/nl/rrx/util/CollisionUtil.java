package nl.rrx.util;

import nl.rrx.entity.Sprite;
import nl.rrx.tile.TileManager;

import java.io.Serial;
import java.io.Serializable;

import static nl.rrx.config.ScreenSettings.TILE_SIZE;

public class CollisionUtil implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private final TileManager tileManager;

    public CollisionUtil(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    /**
     * Check if a sprite is going to hit a collision (i.e. tree, wall etc)
     * @param sprite player, npc etc
     * @return true if a collision will be hit
     */
    public boolean check(Sprite sprite) {
        int spriteLeftWorldX = sprite.getWorldX() + sprite.getSolidArea().x;
        int spriteRightWorldX = sprite.getWorldX() + sprite.getSolidArea().x + sprite.getSolidArea().width;
        int spriteTopWorldY = sprite.getWorldY() + sprite.getSolidArea().y;
        int spriteBottomWorldY = sprite.getWorldY() + sprite.getSolidArea().y + sprite.getSolidArea().height;

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
}
