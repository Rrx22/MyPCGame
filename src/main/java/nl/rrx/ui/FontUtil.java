package nl.rrx.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

class FontUtil {

    // TODO find something prettier at https://fontsgeek.com/
    // Credit for these fonts goes out to https://fontsgeek.com/
    public static final String boldFontFile = "Purisa Bold.ttf";
    public static final String plainFontFile = "Purisa Medium.ttf";

    private FontUtil() {}

    public static Font importFont(String fileName) {
        try {
            InputStream is = FontUtil.class.getResourceAsStream("/font/" + fileName);
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
