package nl.rrx.util;

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
}
