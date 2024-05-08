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

    // PLAYER SETTINGS
    public static final int SPRITE_REFRESH_RATE = 12;
    public static final int PLAYER_RECT_X = TILE_SIZE / 6;
    public static final int PLAYER_RECT_Y = TILE_SIZE / 3;
    public static final int PLAYER_RECT_WIDTH_HEIGHT = (TILE_SIZE / 3) * 2;

    // WORLD SETTINGS
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
}
