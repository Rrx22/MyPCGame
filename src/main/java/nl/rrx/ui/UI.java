package nl.rrx.ui;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Sprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

public class UI {

    private final DependencyManager dm;

    private final Font arial40;
    private final Font arial80B;
    private String currentDialogue = "";

    public UI(DependencyManager dm) {
        this.dm = dm;
        arial40 = new Font("Arial", Font.PLAIN, 32);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial80B);
        g2.setColor(Color.white);

        switch (dm.stateManager.currentState()) {
            case PLAY -> {
                // TODO playstate stuff later
            }
            case PAUSE -> drawPauseScreen(g2);
            case DIALOGUE -> drawDialogueScreen(g2);

        }

    }

    private void drawPauseScreen(Graphics2D g2) {
        String text = "PAUSED";
        int x = getXForCenteredText(g2, text);
        int y = SCREEN_HEIGHT / 2;
        g2.drawString(text, x, y);
    }

    private void drawDialogueScreen(Graphics2D g2) {
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE / 2;
        int width = SCREEN_WIDTH - (TILE_SIZE * 4);
        int height = TILE_SIZE*4;
        drawSubWindow(g2, x, y, width, height);

        x += TILE_SIZE;
        y += TILE_SIZE / 2;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        for (String line : currentDialogue.split("\n")) {
            y += g2.getFontMetrics().getHeight();
            g2.drawString(line, x, y);
        }
    }

    private void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }


    // DEBUG DRAWING
    public void drawDebugStats(Graphics2D g2, long drawStart) {
        g2.setFont(arial40);
        showDrawTime(g2, drawStart);
        Sprite sprite = dm.player;
//        Sprite sprite = dm.npcManager.getNpcs()[0];
        showWorldPositionOf(g2, sprite);
        showIsCollisionOnFor(g2, sprite);
    }

    private void showDrawTime(Graphics2D g2, long drawStart) {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString(String.format("Draw time (seconds): %.5f", (double) passed / 1_000_000_000L), 10, 400);
    }

    private void showWorldPositionOf(Graphics2D g2, Sprite sprite) {
        g2.drawString(String.format("x: %02d, y: %02d", (sprite.getWorldX() / TILE_SIZE), (sprite.getWorldY() / TILE_SIZE)), 10, 440);
    }

    private void showIsCollisionOnFor(Graphics2D g2, Sprite sprite) {
        g2.drawString("collisionOn: " + sprite.isCollisionOn(), 10, 480);
    }
}
