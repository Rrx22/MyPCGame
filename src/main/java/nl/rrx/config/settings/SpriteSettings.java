package nl.rrx.config.settings;

import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class SpriteSettings {

    private SpriteSettings() {}

    public static final int SPRITE_REFRESH_RATE = 12;

    //  - COLLISION BLOCK SIZES
    public static final int PLAYER_RECT_X = TILE_SIZE / 4;
    public static final int PLAYER_RECT_Y = TILE_SIZE / 2;
    public static final int PLAYER_RECT_WIDTH_HEIGHT = (TILE_SIZE / 4) * 2;
    //  - WORLD POSITION
    public static final int INIT_WORLD_X = 28;
    public static final int INIT_WORLD_Y = 28;
    public static final int INIT_SCREEN_X = SCREEN_WIDTH / 2 - (TILE_SIZE / 2);
    public static final int INIT_SCREEN_Y = SCREEN_HEIGHT / 2 - (TILE_SIZE / 2);

    // PLAYER BASE SETTINGS
    public static final Direction PLAYER_START_DIRECTION = Direction.UP;
    public static final int PLAYER_BASE_SPEED = 3;
    public static final int PLAYER_BASE_HP = 10;
    public static final int PLAYER_BASE_STRENGTH = 2;
    public static final int PLAYER_BASE_DEXTERITY = 2;
    public static final int PLAYER_BASE_MAGIC = 2;
    public static final int PLAYER_BASE_SKILLPOINTS = 2;
}
