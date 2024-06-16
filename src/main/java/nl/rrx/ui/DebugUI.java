package nl.rrx.ui;

import nl.rrx.sprite.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class DebugUI {

    private DebugUI() {}

    public static void draw(Graphics2D g2, Sprite sprite, long drawStart, Font font) {
        g2.setFont(font);
        showDrawTime(g2, drawStart);
        showWorldPositionOf(g2, sprite);
        showIsCollisionOnFor(g2, sprite);
    }

    private static void showDrawTime(Graphics2D g2, long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString(String.format("Draw time (seconds): %.5f", (double) passed / 1_000_000_000L), 10, 400);
    }

    private static void showWorldPositionOf(Graphics2D g2, Sprite sprite) {
        g2.drawString(String.format("x: %02d, y: %02d", (sprite.getWorldX() / TILE_SIZE), (sprite.getWorldY() / TILE_SIZE)), 10, 440);
    }

    private static void showIsCollisionOnFor(Graphics2D g2, Sprite sprite) {
        g2.drawString("collisionOn: " + sprite.isCollisionOn(), 10, 480);
    }


}
