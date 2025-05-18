package nl.rrx.config;

import nl.rrx.config.settings.ScreenSettings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FpsHandlerTest {

    private static final int ONE_SECOND_IN_NANOSECONDS = 1_000_000_000;

    private final FpsHandler sut = new FpsHandler();

    @Test
    void fpsHandler_shouldProduceExactlyTheConfiguredAmountOfFramesPerSecond() {
        long timer = 0;
        long framesCount = 0;
        long lastTime = System.nanoTime();

        while (timer < ONE_SECOND_IN_NANOSECONDS) {
            if (sut.canLoadNextFrame()) {
                framesCount++;
            }
            long currentTime = System.nanoTime();
            timer += currentTime - lastTime;
            lastTime = currentTime;
        }

        assertEquals(ScreenSettings.FPS, framesCount);
    }

}