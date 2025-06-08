package nl.rrx.sprite.Player;

import nl.rrx.config.settings.ScreenSettings;
import nl.rrx.object.Stashable;
import nl.rrx.sound.SoundEffect;
import nl.rrx.ui.FloatingBattleMessagesUI;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;

public class Stash {
    public final static int MAX = 20;
    private final static Stashable[] items = new Stashable[20];

    private int warningMessageCounter = 0;

    public boolean addToStash(Stashable item) {
        if (!item.canStash()) {
            throw new RuntimeException("An object was passed which should not be stashed!");
        }
        if (warningMessageCounter > ScreenSettings.FPS) {
            warningMessageCounter = 0;
        }

        for (int i = 0; i <= MAX - 1; i++) {
            if (items[i] == null) {
                warningMessageCounter = 0;
                items[i] = item;
                FloatingBattleMessagesUI.add(PLAYER, "Got a " + item.title() + "!", FloatingBattleMessagesUI.MessageType.PLAYER_INFO);
                SOUND_HANDLER.playSoundEffect(SoundEffect.COIN);
                return true;
            }
        }
        if (warningMessageCounter == 0) {
            FloatingBattleMessagesUI.add(PLAYER, "Stash is full", FloatingBattleMessagesUI.MessageType.PLAYER_INFO);
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
        }
        warningMessageCounter ++;
        System.out.println("warningMessageCounter: " + warningMessageCounter);
        return false;
    }

    public Stashable[] items() {
        return items;
    }

}
