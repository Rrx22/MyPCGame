package nl.rrx.ui;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

public class UI {

    private final DependencyManager dm;
    private Graphics2D g2; // todo this is bad

    private final Font arial40;
    private final Font arial80B;

    public UI(DependencyManager dm) {
        this.dm = dm;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial80B);
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
        int x = getXForCenteredText(g2, text);
        int y = SCREEN_HEIGHT / 2;
        g2.drawString(text, x, y);
    }


    // DEBUG DRAWING
    public void drawDebugStats(long drawStart) {
        g2.setFont(arial40);
        showDrawTime(drawStart);
        Sprite sprite = dm.player;
//        Sprite sprite = dm.npcManager.getNpcs()[0];
        showWorldPositionOf(sprite);
        showIsCollisionOnFor(sprite);
    }

    private void showDrawTime(long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString(String.format("Draw time (seconds): %.5f", (double) passed / 1_000_000_000L), 10, 400);
    }

    private void showWorldPositionOf(Sprite sprite) {
        g2.drawString(String.format("x: %02d, y: %02d", (sprite.getWorldX() / TILE_SIZE), (sprite.getWorldY() / TILE_SIZE)), 10, 440);
    }

    private void showIsCollisionOnFor(Sprite sprite) {
        g2.drawString("collisionOn: " + sprite.isCollisionOn(), 10, 480);
    }
}
