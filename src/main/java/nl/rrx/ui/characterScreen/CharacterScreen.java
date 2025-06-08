package nl.rrx.ui.characterScreen;

import nl.rrx.sprite.Direction;

import java.awt.Font;
import java.awt.Graphics2D;

public class CharacterScreen {

    private static FocusFrame focusFrame = FocusFrame.STATS;
    private static StatsFrame statsFrame;
    private static StashFrame stashFrame;

    private CharacterScreen() {
    }

    public static void draw(Graphics2D g2, Font font) {
        statsFrame.draw(g2, font);
        stashFrame.draw(g2);
    }

    public static void changeFocus() {
        focusFrame = switch (focusFrame) {
            case STATS -> FocusFrame.STASH;
            case STASH -> FocusFrame.STATS;
        };
    }

    public static void moveCursor(Direction direction) {
        Interactable interactable = switch (focusFrame) {
            case STATS -> statsFrame;
            case STASH -> stashFrame;
        };
        interactable.moveCursor(direction);
    }

    public static void init() {
        statsFrame = new StatsFrame();
        stashFrame = new StashFrame();
        focusFrame = FocusFrame.STATS;
    }

    private enum FocusFrame {
        STATS,
        STASH,
        // todo extend with SPELLS ?
    }
}
