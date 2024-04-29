package nl.rrx.utils;

public class FpsUtil {

    private final double interval;

    private long lastTime = System.nanoTime();
    private double delta = 0L;

    public FpsUtil(int fps) {
        interval = (double) 1000000000 / fps; // 1 second divided by FPS
    }

    public boolean canLoadNextFrame() {
        long currentTime = System.nanoTime();
        delta += (currentTime - lastTime) / interval;
        lastTime = currentTime;

        boolean canLoadNextFrame = delta >= 1;
        if (canLoadNextFrame) {
            delta--;
        }
        return canLoadNextFrame;
    }
}
