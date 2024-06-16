package nl.rrx.ui;

import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

class PauseUI {

    private PauseUI() {}

    static void draw(Graphics2D g2, Font font) {
        String text = "PAUSED";
        g2.setFont(font);
        int x = getXForCenteredText(g2, text);
        int y = SCREEN_HEIGHT / 2;
        g2.drawString(text, x, y);
    }
}
