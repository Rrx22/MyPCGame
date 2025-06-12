package nl.rrx.sprite.Player;

import nl.rrx.config.settings.ScreenSettings;
import nl.rrx.object.loot.Item;
import nl.rrx.sound.SoundEffect;
import nl.rrx.ui.FloatingBattleMessages;

import java.util.Arrays;
import java.util.Optional;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;

public class Stash {
    public final static int MAX = 20;
    private final static Item[] items = new Item[20];

    private int warningMessageCounter = 0;

    public boolean addToStash(Item item) {
        if (warningMessageCounter > ScreenSettings.FPS) {
            warningMessageCounter = 0;
        }

        for (int i = 0; i <= MAX - 1; i++) {
            if (items[i] == null) {
                warningMessageCounter = 0;
                items[i] = item;
                FloatingBattleMessages.add(PLAYER, "Got a " + item.title + "!", FloatingBattleMessages.MessageType.PLAYER_INFO);
                SOUND_HANDLER.playSoundEffect(SoundEffect.COIN);
                return true;
            }
        }
        if (warningMessageCounter == 0) {
            FloatingBattleMessages.add(PLAYER, "Stash is full", FloatingBattleMessages.MessageType.PLAYER_INFO);
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
        }
        warningMessageCounter++;
        return false;
    }

    public void remove(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    public Item[] items() {
        return items;
    }

    public <T extends Item> Optional<T> findFirst(Class<T> clazz) {
        return Arrays.stream(items)
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }
}
