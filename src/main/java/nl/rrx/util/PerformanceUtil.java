package nl.rrx.util;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PerformanceUtil {

    private PerformanceUtil() {
    }

    public static BufferedImage getScaledImage(String imageUri, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(PerformanceUtil.class.getResourceAsStream(imageUri));
            BufferedImage scaledImage = new BufferedImage(width, height, image.getType());

            Graphics2D g2 = scaledImage.createGraphics();
            g2.drawImage(image, 0, 0, width, height, null);
            g2.dispose();

            return scaledImage;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
