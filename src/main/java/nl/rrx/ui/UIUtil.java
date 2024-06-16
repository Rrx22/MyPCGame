package nl.rrx.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

class UIUtil {

    // TODO find something prettier at https://fontsgeek.com/
    // Credit for these fonts goes out to https://fontsgeek.com/
    public static final String PURISA_BOLD_TTF = "Purisa Bold.ttf";
    public static final String PURISA_MEDIUM_TTF = "Purisa Medium.ttf";

    private UIUtil() {}

    public static Font importFont(String fileName) {
        try {
            InputStream is = UIUtil.class.getResourceAsStream("/font/" + fileName);
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 210);
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
}
