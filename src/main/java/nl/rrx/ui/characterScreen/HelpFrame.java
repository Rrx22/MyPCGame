package nl.rrx.ui.characterScreen;

import nl.rrx.ui.UIUtil;
import nl.rrx.util.ScreenUtil;

import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

class HelpFrame {

    private boolean showHelp;

    void draw(Graphics2D g2, Font font) {
        int x = TILE_SIZE * 2;
        int y = TILE_SIZE * 2;
        int width = SCREEN_WIDTH - (TILE_SIZE * 4);
        int height = TILE_SIZE * 5;
        UIUtil.drawSubWindow(g2, x, y, width, height);

        String[] helpLineKeys = new String[] {
                "[ WASD/arrows ]",
                "[ ENTER ]",
                "[ C ]",
                "[ Q ]",
                "[ H ]",
        };
        String[] helpLineValues = new String[] {
                "Navigate inside window",
                "Do an action on the selected item",
                "Switch between Stats/Stash/Spells windows",
                "Quit Character screen",
                "Toggle Help",
        };

        x += TILE_SIZE;
        y += TILE_SIZE / 2;
        g2.setFont(font);
        for (int i = 0; i < helpLineKeys.length; i++) {
            String key = helpLineKeys[i];
            String instruction = helpLineValues[i];
            y += g2.getFontMetrics().getHeight();
            g2.drawString(key, x, y);
            g2.drawString(instruction, x + ScreenUtil.getTextWidth(g2, key) + TILE_SIZE/3, y);
        }
    }

    public void drawHelpString(Graphics2D g2, Font font) {
        int x = TILE_SIZE * 3;
        int y = TILE_SIZE * 12 - TILE_SIZE / 2;
        String text = "[ H ]elp";
        g2.setFont(font.deriveFont(Font.BOLD));
        g2.drawString(text, x, y);
    }

    public boolean show() {
        return showHelp;
    }

    void toggleHelp() {
        showHelp = !showHelp;
    }
}
