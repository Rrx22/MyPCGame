package nl.rrx.util;

import nl.rrx.sprite.AttackUtil;
import nl.rrx.sprite.Sprite;

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

    public boolean doAttackAnimation(Sprite sprite, AttackUtil attackUtil) {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
            return true;
        }
        if (spriteCounter <= 25) {
            spriteNum = 2;
            attackUtil.handleAttack(sprite);
            return true;
        }
        spriteNum = 1;
        spriteCounter = 0;
        return false;
    }
}
