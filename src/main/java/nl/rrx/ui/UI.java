package nl.rrx.ui;

import nl.rrx.object.GameObjectType;
import nl.rrx.sprite.Player;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.FPS;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class UI {

    private static final int TWO_SECONDS = FPS * 2;

    private final Player player;
    private final BufferedImage keyImage;
    private final Font arial40;
    private final Font arial80B;

    private String message = "";
    private int messageCounter;

    private double playTime;

    public UI(Player player) {
        this.player = player;
        keyImage = PerformanceUtil.getScaledImage(GameObjectType.KEY.imageUri, TILE_SIZE, TILE_SIZE);
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String message) {
        this.message = message;
    }

    public void draw(Graphics2D g2) {

        if (player.isGameOver()) {
            printCentralizedText(g2, "You found the treasure!", -3, arial40);
            printCentralizedText(g2, "Congratulations", 2, arial80B);
            printCentralizedText(g2, String.format("Your time is: %.2f!", playTime), 4, arial40);
            return;
        }

        // KEYS
        g2.setFont(arial40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, TILE_SIZE / 2, TILE_SIZE / 2, null);
        g2.drawString("x " + player.getKeysInInventory(), 75, 65);

        // TIME
        playTime += (double) 1/FPS;
        g2.drawString(String.format("Time: %.2f", playTime), TILE_SIZE *11, 65);

        // MESSAGE
        if (!message.isEmpty()) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, TILE_SIZE / 2, TILE_SIZE * 5);
            displayMessageFor(TWO_SECONDS);
        }

    }

    private void printCentralizedText(Graphics2D g2, String text, int yRelativeToCenter, Font font) {
        g2.setFont(font);
        g2.setColor(font == arial40 ? Color.white : Color.yellow);
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = SCREEN_WIDTH / 2 - textLength / 2;
        int y = SCREEN_HEIGHT / 2 + TILE_SIZE * yRelativeToCenter;
        g2.drawString(text, x, y);
    }

    private void displayMessageFor(int duration) {
        messageCounter++;
        if (messageCounter > duration) {
            messageCounter = 0;
            message = "";
        }
    }
}
