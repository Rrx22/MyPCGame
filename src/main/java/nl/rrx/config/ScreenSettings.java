package nl.rrx.config;

import java.awt.Dimension;

public class ScreenSettings {

    private ScreenSettings() {}

    // SCREEN SETTINGS
    private static final int FPS = 60;
    private static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private static final int SCALE = 3;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    private static final int MAX_SCREEN_COL = 16;
    private static final int MAX_SCREEN_ROW = 12;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    private static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

    public static Dimension getScreenSize() {
        return SCREEN_SIZE;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getFps() {
        return FPS;
    }
}
