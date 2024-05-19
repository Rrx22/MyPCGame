package nl.rrx.util;

import nl.rrx.sprite.Player;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class ScreenUtil {

    private ScreenUtil() {}

    public static boolean isWithinScreenBoundary(Player player, int worldX, int worldY) {
        return worldX > player.getWorldX() - player.getScreenX() - TILE_SIZE
                && worldX < player.getWorldX() + player.getScreenX() + TILE_SIZE
                && worldY > player.getWorldY() - player.getScreenY() - TILE_SIZE
                && worldY < player.getWorldY() + player.getScreenY() + TILE_SIZE;
    }
}
