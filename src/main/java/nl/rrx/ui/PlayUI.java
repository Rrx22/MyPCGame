package nl.rrx.ui;

import nl.rrx.object.GameObjectType;
import nl.rrx.util.PerformanceUtil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

class PlayUI {

    private static final BufferedImage heartFull = PerformanceUtil.getScaledImage(GameObjectType.HEART.imageUri, TILE_SIZE, TILE_SIZE);
    private static final BufferedImage heartHalf = PerformanceUtil.getScaledImage("/images/objects/heart_half.png", TILE_SIZE, TILE_SIZE);
    private static final BufferedImage heartBlank = PerformanceUtil.getScaledImage("/images/objects/heart_blank.png", TILE_SIZE, TILE_SIZE);

    private PlayUI() {
    }

    static void draw(Graphics2D g2) {
        drawPlayerLife(g2);
    }

    private static void drawPlayerLife(Graphics2D g2) {
        final int playerHP = PLAYER.getHealthPoints();
        final int playerHearts = PLAYER.getMaxHP() / 2;
        int x = TILE_SIZE / 2;
        int y = TILE_SIZE / 2;

        for (int i = 0; i < playerHearts; i++) {
            if (playerHP >= (i + 1) * 2) {
                g2.drawImage(heartFull, x, y, null);
            } else if (playerHP >= (i * 2) + 1) {
                g2.drawImage(heartHalf, x, y, null);
            } else {
                g2.drawImage(heartBlank, x, y, null);
            }
            x += TILE_SIZE;
        }
    }
}
