package nl.rrx.ui;

import nl.rrx.config.DependencyManager;
import nl.rrx.state.GameState;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

public class TitleScreen {

    private static final BufferedImage WIZARD_IMAGE = PerformanceUtil.getScaledImage("/images/npc/wizard-down-2.png", TILE_SIZE, TILE_SIZE);
    private static final int INDEX_LAST_MENU_ITEM = 2;

    private static int selectedMenuItemIndex;

    private TitleScreen() {
    }

    public static void draw(Graphics2D g2, Font fontBold) {
        setBackgound(g2, new Color(0, 0, 0));
        drawTitle(g2, "ADVENTURE STORY", fontBold);
        drawImage(g2);

        g2.setFont(fontBold.deriveFont(30F));
        drawMenuItem(g2, "NEW GAME", 0);
        drawMenuItem(g2, "LOAD GAME", 1);
        drawMenuItem(g2, "QUIT GAME", INDEX_LAST_MENU_ITEM);
    }

    public static void pressUp() {
        if (TitleScreen.selectedMenuItemIndex > 0) TitleScreen.selectedMenuItemIndex--;
    }

    public static void pressDown() {
        if (TitleScreen.selectedMenuItemIndex < INDEX_LAST_MENU_ITEM) TitleScreen.selectedMenuItemIndex++;
    }

    public static void pressMenu(DependencyManager dm) {
        switch (selectedMenuItemIndex) {
            case 0 -> { //START GAME
                dm.soundManager.playMusic();
                dm.stateManager.setState(GameState.PLAY);
            }
            case 1 -> { //LOAD GAME
                // TODO LOAD GAME
            }
            case 2 -> System.exit(0); // QUIT
            default -> throw new IllegalArgumentException("selectedMenuItemIndex should be between 0-" + INDEX_LAST_MENU_ITEM);
        }
    }

    private static void setBackgound(Graphics2D g2, Color c) {
        g2.setColor(c);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private static void drawTitle(Graphics2D g2, String title, Font fontBold) {
        g2.setFont(fontBold.deriveFont(60F));
        int x = getXForCenteredText(g2, title);
        int y = TILE_SIZE * 3;
        g2.setColor(new Color(100, 100, 100));//shadow
        g2.drawString(title, x + 5, y + 5);//shadow
        g2.setColor(new Color(255, 255, 255));
        g2.drawString(title, x, y);

    }

    private static void drawImage(Graphics2D g2) {
        int x = SCREEN_WIDTH / 2 - TILE_SIZE;
        int y = TILE_SIZE * 5;
        g2.drawImage(WIZARD_IMAGE, x, y, TILE_SIZE * 2, TILE_SIZE * 2, null);
    }

    private static void drawMenuItem(Graphics2D g2, String text, int index) {
        int x = getXForCenteredText(g2, text);
        int y = (int) (TILE_SIZE * (8.5 + index));
        g2.drawString(text, x, y);
        if (selectedMenuItemIndex == index) {
            g2.drawString(">", x - TILE_SIZE, y);
        }
    }
}
