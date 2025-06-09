package nl.rrx.ui.characterScreen;

import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.Direction;

import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;

public class UI_Character {

    private static FocusFrame focusFrame = FocusFrame.STATS;

    private static HelpFrame helpScreen;
    private static StatsFrame statsFrame;
    private static StashFrame stashFrame;

    private UI_Character() {
    }

    public static void draw(Graphics2D g2, Font font) {
        if (helpScreen.show()) {
            helpScreen.draw(g2, font);
            return;
        }
        statsFrame.draw(g2, font, focusFrame == FocusFrame.STATS);
        stashFrame.draw(g2, focusFrame == FocusFrame.STASH);
        helpScreen.drawHelpString(g2, font);
    }

    public static void changeFocus() {
        focusFrame = switch (focusFrame) {
            case STATS -> FocusFrame.STASH;
            case STASH -> FocusFrame.STATS;
        };
        SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
    }

    public static void moveCursor(Direction direction) {
        if (polymorph().moveCursor(direction)) {
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
        }
    }

    public static void doAction() {
        polymorph().doAction();
        SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
    }

    public static void init() {
        helpScreen = new HelpFrame();
        statsFrame = new StatsFrame();
        stashFrame = new StashFrame();
        focusFrame = PLAYER.skillPoints > 0 ? FocusFrame.STATS : FocusFrame.STASH;
    }

    public static void clear() {
        helpScreen = null;
        statsFrame = null;
        stashFrame = null;
        focusFrame = null;
    }

    public static void toggleHelp() {
        helpScreen.toggleHelp();
    }

    private static Interactable polymorph() {
        return switch (focusFrame) {
            case STATS -> statsFrame;
            case STASH -> stashFrame;
        };
    }

    private enum FocusFrame {
        STATS,
        STASH,
        // todo extend with SPELLS ?
    }
}
