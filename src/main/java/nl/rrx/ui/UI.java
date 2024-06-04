package nl.rrx.ui;

import nl.rrx.config.DependencyManager;
import nl.rrx.object.GameObjectType;
import nl.rrx.sprite.Player;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.FPS;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

public class UI {

    private static final int TWO_SECONDS = FPS * 2;

    private final DependencyManager dm;
    private Graphics2D g2;
    private final Font arial40;
    private final Font arial80B;

    private String message = "";
    private int messageCounter;

    private double playTime;

    public UI(DependencyManager dm) {
        this.dm = dm;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String message) {
        this.message = message;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
        g2.setColor(Color.white);

        if (!dm.keyHandler.isPauseGame()) {
            // Do playstate stuff later
        }
        if (dm.keyHandler.isPauseGame()) {
            drawPauseScreen();
        }

    }

    private void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenteredText(dm.player, g2, text);
        int y = SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
    }



    private void displayMessageFor(int duration) {
        messageCounter++;
        if (messageCounter > duration) {
            messageCounter = 0;
            message = "";
        }
    }

    // DEBUG DRAWING
    public void drawDebugStats(Graphics2D g2, long drawStart) {
        showDrawtime(g2, drawStart);
        showPlayerWorldPosition(g2);
    }

    private static void showDrawtime(Graphics2D g2, long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString(String.format("Draw time (seconds): %.5f", (double) passed / 1_000_000_000L), 10, 400);
    }

    private void showPlayerWorldPosition(Graphics2D g2) {
        g2.drawString(String.format("x: %02d, y: %02d", (dm.player.getWorldX() / TILE_SIZE), (dm.player.getWorldY() / TILE_SIZE)), 10, 440);
    }
}
