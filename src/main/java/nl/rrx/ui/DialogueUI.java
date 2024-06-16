package nl.rrx.ui;

import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

class DialogueUI {

    private DialogueUI() {}

    public static String currentDialogue = "";

    public static void draw(Graphics2D g2, Font font) {
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE / 2;
        int width = SCREEN_WIDTH - (TILE_SIZE * 4);
        int height = TILE_SIZE*4;
        UIUtil.drawSubWindow(g2, x, y, width, height);

        x += TILE_SIZE;
        y += TILE_SIZE / 2;
        g2.setFont(font);
        for (String line : currentDialogue.split("\n")) {
            y += g2.getFontMetrics().getHeight();
            g2.drawString(line, x, y);
        }
    }
}
