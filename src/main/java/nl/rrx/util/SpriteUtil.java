package nl.rrx.util;

import java.io.Serial;
import java.io.Serializable;

public class SpriteUtil implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    private int spriteCounter = 0;
    private int spriteNum = 1;

    public void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 15) {
            spriteNum = (spriteNum != 1) ? 1 : 2;
            spriteCounter = 0;
        }
    }

    public boolean isNewDirection() {
        return spriteNum == 1;
    }
}
