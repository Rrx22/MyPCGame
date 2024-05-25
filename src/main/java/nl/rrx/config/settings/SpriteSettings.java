package nl.rrx.config.settings;

import nl.rrx.sprite.Direction;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class SpriteSettings {

    private SpriteSettings() {}

    public static final int SPRITE_REFRESH_RATE = 24;

    // COLLISION BLOCK SIZES
    public static final int PLAYER_RECT_X = TILE_SIZE / 4;
    public static final int PLAYER_RECT_Y = TILE_SIZE / 2;
    public static final int PLAYER_RECT_WIDTH_HEIGHT = (TILE_SIZE / 4) * 2;

    // PLAYER WORLD POSITION
    public static final int INIT_WORLD_X = TILE_SIZE * 23;
    public static final int INIT_WORLD_Y = TILE_SIZE * 21;
    public static final int INIT_SCREEN_X = SCREEN_WIDTH / 2 - (TILE_SIZE / 2);
    public static final int INIT_SCREEN_Y = SCREEN_HEIGHT / 2 - (TILE_SIZE / 2);
    public static final int INIT_SPEED = 4;
    public static final Direction INIT_DIRECTION = Direction.DOWN;
}
