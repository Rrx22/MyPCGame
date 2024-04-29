package nl.rrx.util;

public class FpsUtil {

    public static final int ONE_SECOND_IN_NANOSECONDS = 1_000_000_000;

    private final double interval;

    private long lastTime = System.nanoTime();

    public FpsUtil(int fps) {
        interval = (double) ONE_SECOND_IN_NANOSECONDS / fps;
    }

    public boolean canLoadNextFrame() {
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;
        boolean canLoadNextFrame = elapsedTime >= interval;

        if (canLoadNextFrame) {
            lastTime = currentTime;
        }
        return canLoadNextFrame;
    }
}
