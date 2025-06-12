package nl.rrx.config.settings;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class WorldSettings {

    private WorldSettings() {}

    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;

    // GAME OBJECTS
    public static final int MAX_NPCS = 10;
    public static final int MAX_MONSTERS = 20;

    // EVENT
    public static final int DEFAULT_EVENT_SIZE = TILE_SIZE / 3;
    public static final int DEFAULT_EVENT_OUTLINER = TILE_SIZE / 3 - 1;

}
