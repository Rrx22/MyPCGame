package nl.rrx.config;

import nl.rrx.config.settings.ScreenSettings;

public class FpsHandler {

    private static final int ONE_SECOND_IN_NANOSECONDS = 1_000_000_000;
    private static final double INTERVAL = (double) ONE_SECOND_IN_NANOSECONDS / ScreenSettings.FPS;

    private long lastTime = System.nanoTime();

    public boolean canLoadNextFrame() {
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;
        boolean canLoadNextFrame = elapsedTime >= INTERVAL;

        if (canLoadNextFrame) {
            lastTime = currentTime;
        }
        return canLoadNextFrame;
    }
}
