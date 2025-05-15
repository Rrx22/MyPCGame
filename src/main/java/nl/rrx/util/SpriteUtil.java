package nl.rrx.util;

import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Sprite;

import java.awt.Rectangle;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.SpriteSettings.SPRITE_REFRESH_RATE;

public class SpriteUtil {

    private int spriteCounter = 0;
    private int spriteNum = 0;
    private boolean isAlreadyUpdatedDuringFrame; // do not update twice when holding UP|DOWN & LEFT|RIGHT at the same time

    public void updateSprite() {
        if (isAlreadyUpdatedDuringFrame) {
            return;
        }
        isAlreadyUpdatedDuringFrame = true;

        spriteCounter++;
        if (spriteCounter > SPRITE_REFRESH_RATE) {
            spriteNum = (spriteNum == 1) ? 0 : 1;
            spriteCounter = 0;
        }
    }

    public boolean isNewDirection() {
        this.isAlreadyUpdatedDuringFrame = false;
        return spriteNum == 1;
    }

    public void standStill() {
        spriteNum = 1;
    }

    public boolean attack(Sprite sprite, Rectangle collisionArea, Rectangle attackArea, Direction direction) {
        spriteCounter++;
        if (spriteCounter <= 3) {
            spriteNum = 1;
            return true;
        }
        if (spriteCounter <= SPRITE_REFRESH_RATE * 2) {
            spriteNum = 2;

            // save current info to reset after hit
            int currentWorldX = sprite.getWorldX();
            int currentWorldY = sprite.getWorldY();
            int collisionAreaWidth = collisionArea.width;
            int collisionAreaHeight = collisionArea.height;

            // adjust sprite's worldX/Y for attack area
            switch(direction) {
                case UP -> sprite.setWorldY(sprite.getWorldY() - attackArea.height);
                case DOWN -> sprite.setWorldY(sprite.getWorldY() + attackArea.height);
                case LEFT -> sprite.setWorldX(sprite.getWorldX() - attackArea.width);
                case RIGHT -> sprite.setWorldX(sprite.getWorldX() + attackArea.width);
            }
            collisionArea.width = attackArea.width;
            collisionArea.height = attackArea.height;

            COLLISION_UTIL.checkSprite(PLAYER, MONSTER_MGR.getMonsters());

            // restore original settings
            sprite.setWorldX(currentWorldX);
            sprite.setWorldY(currentWorldY);
            collisionArea.width = collisionAreaWidth;
            collisionArea.height = collisionAreaHeight;

            return true;
        }
        spriteNum = 1;
        spriteCounter = 0;
        return false;
    }
}
