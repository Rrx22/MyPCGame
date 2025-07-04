package nl.rrx.ui;

import nl.rrx.state.GameState;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_HEIGHT;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_WIDTH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForCenteredText;

public class UI_Title {

    // todo refactor to use Interactable interface like the Character Screen
    //  also get rid of class selection, since this is no longer the way to go
    //  in fact - need to completely rethink the title page

    private static final BufferedImage KNIGHT_IMAGE = PerformanceUtil.getScaledImage("/images/sprite/knight/down-2.png", TILE_SIZE, TILE_SIZE);
    private static final BufferedImage WIZARD_IMAGE = PerformanceUtil.getScaledImage("/images/sprite/wizard/down-2.png", TILE_SIZE, TILE_SIZE);
    private static final BufferedImage ROGUE_IMAGE = PerformanceUtil.getScaledImage("/images/sprite/rogue/down-2.png", TILE_SIZE, TILE_SIZE);
    private static final BufferedImage BOY_IMAGE = PerformanceUtil.getScaledImage("/images/sprite/giant/down-2.png", TILE_SIZE, TILE_SIZE);
    private static final int INDEX_LAST_MENU_ITEM = 2;
    private static final int LAST_INDEX_CLASS_SELECTION = 3;

    private static int selectedItemNum;
    private static int titleScreenFlowNum;

    private UI_Title() {
    }

    static void draw(Graphics2D g2, Font fontBold) {
        if (titleScreenFlowNum == 0) {
            drawScreen(g2, fontBold, "ADVENTURE STORY", WIZARD_IMAGE, "NEW GAME", "LOAD GAME", "QUIT");
        } else if (titleScreenFlowNum == 1) {
            drawScreen(g2, fontBold, "Select Your Class", selectImage(), "Knight", "Wizard", "Rogue", "Back");
        }
    }

    private static void drawScreen(Graphics2D g2, Font fontBold, String title, BufferedImage image, String ... menuItems) {
        setBackground(g2, new Color(0, 0, 0));
        drawTitle(g2, title, fontBold);
        drawImage(g2, image);

        g2.setFont(fontBold.deriveFont(30F));
        for (int i = 0; i < menuItems.length; i++) {
            drawMenuItem(g2, menuItems[i], i);
        }
    }

    public static void pressUp() {
        if (UI_Title.selectedItemNum > 0) UI_Title.selectedItemNum--;
    }

    public static void pressDown() {
        int lastIndex = titleScreenFlowNum == 0
                ? INDEX_LAST_MENU_ITEM
                : LAST_INDEX_CLASS_SELECTION;
        if (UI_Title.selectedItemNum < lastIndex) UI_Title.selectedItemNum++;
    }

    public static void pressMenu() {
        if (titleScreenFlowNum == 0) {
            handleTitleScreen();
        } else if (titleScreenFlowNum == 1) {
            handleClassSelectionScreen();
        }
    }

    private static void handleTitleScreen() {
        switch (selectedItemNum) {
            case 0 ->  titleScreenFlowNum++; //START GAME
            case 1 -> {
                // add LOAD GAME functionality
            }
            case 2 -> System.exit(0); // QUIT
            default -> throw new IllegalArgumentException("selectedMenuItemIndex should be between 0-" + INDEX_LAST_MENU_ITEM);
        }
    }

    private static void handleClassSelectionScreen() {
        if (selectedItemNum == LAST_INDEX_CLASS_SELECTION) {
            titleScreenFlowNum--;
            selectedItemNum = 0;
        } else {
            String playerClass = switch (selectedItemNum) {
                case 0 -> "knight";
                case 1 -> "wizard";
                case 2 -> "rogue";
                default -> "boy";
            };
//            PLAYER.loadPlayerImages(playerClass);
            SOUND_HANDLER.playMusic();
            STATE_HANDLER.setState(GameState.PLAY);
        }
    }

    private static void setBackground(Graphics2D g2, Color c) {
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

    private static void drawImage(Graphics2D g2, BufferedImage image) {
        int x = SCREEN_WIDTH / 2 - TILE_SIZE;
        int y = TILE_SIZE * 5;
        g2.drawImage(image, x, y, TILE_SIZE * 2, TILE_SIZE * 2, null);
    }

    private static void drawMenuItem(Graphics2D g2, String text, int index) {
        int x = getXForCenteredText(g2, text);
        int y = (int) (TILE_SIZE * (8.5 + index));
        g2.drawString(text, x, y);
        if (selectedItemNum == index) {
            g2.drawString(">", x - TILE_SIZE, y);
        }
    }

    private static BufferedImage selectImage() {
        return switch (selectedItemNum) {
            case 0 -> KNIGHT_IMAGE;
            case 1 -> WIZARD_IMAGE;
            case 2 -> ROGUE_IMAGE;
            default -> BOY_IMAGE;
        };
    }
}
