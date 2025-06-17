package nl.rrx.object.loot.otherItems;

import nl.rrx.object.loot.Item;
import nl.rrx.sound.SoundEffect;

import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.DependencyManager.UI;

public abstract class HealthPotion extends Item implements Consumable {

    private final int heal;

    public HealthPotion(BufferedImage image, String title, int heal) {
        super(image, title, "Restores " + heal + " HP.");
        this.heal = heal;
    }

    @Override
    public void consume() {
        if (PLAYER.getHealthPoints() == PLAYER.getMaxHP()) {
            UI.showDialogue("You already are at full HP!");
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
            return;
        }
        PLAYER.recoverHP(heal);
        STASH.remove(this);
        SOUND_HANDLER.playSoundEffect(SoundEffect.POWERUP);
        UI.showDialogue("You've used your potion.", "It restored 2 HP!");
    }
}
