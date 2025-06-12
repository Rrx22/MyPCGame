package nl.rrx.object.placed;

import nl.rrx.object.loot.Item;
import nl.rrx.object.loot.otherItems.OBJ_Key;
import nl.rrx.sound.SoundEffect;

import java.util.Optional;

import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.DependencyManager.UI;

public class OBJ_Door extends PlacedObject {

    public OBJ_Door(String imageUri, boolean isCollision, int worldX, int worldY, int offsetX, int offsetY) {
        super(imageUri, isCollision, worldX, worldY, offsetX, offsetY);
    }

    @Override
    public void interact() {
        Optional<OBJ_Key> key = STASH.findFirst(OBJ_Key.class);
        if (key.isPresent()) {
            openDoor(key.get());
            SOUND_HANDLER.playSoundEffect(SoundEffect.UNLOCK);
        } else {
            UI.showDialogue("You need a key to enter", "this door.");
        }
    }

    private void openDoor(Item key) {
        OBJECT_MGR.remove(this);
        STASH.remove(key);
    }
}
