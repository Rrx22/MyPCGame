package nl.rrx.config;

import java.awt.Dimension;

public class ScreenSettings {

    private ScreenSettings() {}

    // SCREEN SETTINGS
    public static final int FPS = 60;
    public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

    public static final int SPRITE_REFRESH_RATE = 12;
}
