package nl.rrx.util;

import static nl.rrx.config.settings.SpriteSettings.SPRITE_REFRESH_RATE;

public class SpriteUtil {

    private int spriteCounter = 0;
    private int spriteNum = 1;

    public void updateSprite() {
        spriteCounter++;
        if (spriteCounter > SPRITE_REFRESH_RATE) {
            spriteNum = (spriteNum != 1) ? 1 : 2;
            spriteCounter = 0;
        }
    }

    public boolean isNewDirection() {
        return spriteNum == 1;
    }
}
