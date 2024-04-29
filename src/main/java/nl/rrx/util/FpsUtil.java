package nl.rrx.util;

import nl.rrx.config.ScreenSettings;

public class FpsUtil {

    private static final int ONE_SECOND_IN_NANOSECONDS = 1_000_000_000;
    private static final double INTERVAL = (double) ONE_SECOND_IN_NANOSECONDS / ScreenSettings.getFps();

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
