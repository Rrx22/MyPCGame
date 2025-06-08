package nl.rrx.util;

import nl.rrx.sprite.Player.Player;

import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class ScreenUtil {

    private ScreenUtil() {}

    public static boolean isWithinScreenBoundary(Player player, int worldX, int worldY) {
        return worldX > player.getWorldX() - player.getScreenX() - TILE_SIZE
                && worldX < player.getWorldX() + player.getScreenX() + TILE_SIZE
                && worldY > player.getWorldY() - player.getScreenY() - TILE_SIZE
                && worldY < player.getWorldY() + player.getScreenY() + TILE_SIZE;
    }

    public static int getXForCenteredText(Graphics2D g2, String text) {
        int textLength = getTextWidth(g2, text);
        return SCREEN_WIDTH / 2 - textLength /2;
    }

    public static int getXForAlignToRightText(Graphics2D g2, String text, int tailX) {
        int textLength = getTextWidth(g2, text);
        return tailX - textLength;
    }

    public static int getTextWidth(Graphics2D g2, String text) {
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }

    public static int getScreenX(int worldX) {
        return worldX - PLAYER.getWorldX() + PLAYER.getScreenX();
    }

    public static int getScreenY(int worldY) {
        return worldY - PLAYER.getWorldY() + PLAYER.getScreenY();
    }
}
