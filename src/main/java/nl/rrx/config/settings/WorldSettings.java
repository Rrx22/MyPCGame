package nl.rrx.config.settings;

import static nl.rrx.config.settings.ScreenSettings.MAX_SCREEN_COL;
import static nl.rrx.config.settings.ScreenSettings.MAX_SCREEN_ROW;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class WorldSettings {

    private WorldSettings() {}

    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
}
