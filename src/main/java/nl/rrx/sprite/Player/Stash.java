package nl.rrx.sprite.Player;

import nl.rrx.object.Stashable;
import nl.rrx.ui.FloatingBattleMessagesUI;

import static nl.rrx.config.DependencyManager.PLAYER;

public class Stash {
    public final static int MAX = 20;
    private final static Stashable[] items = new Stashable[20];

    void addToStash(Stashable item) {
        if (!item.canStash()) {
            throw new RuntimeException("An object was passed which should not be stashed!");
        }

        for (int i = 0; i <= MAX - 1; i++) {
            if (items[i] == null) {
                items[i] = item;
                return;
            }
        }
        FloatingBattleMessagesUI.add(PLAYER, "Stash is full", FloatingBattleMessagesUI.MessageType.PLAYER_INFO);
    }

    public Stashable[] items() {
        return items;
    }

}
