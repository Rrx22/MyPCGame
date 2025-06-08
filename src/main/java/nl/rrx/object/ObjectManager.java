package nl.rrx.object;

import nl.rrx.object.item.ShieldFactory;
import nl.rrx.object.item.WeaponFactory;
import nl.rrx.object.item.otherItems.ItemFactory;
import nl.rrx.object.world.LootObject;
import nl.rrx.object.world.PlacedObject;
import nl.rrx.object.world.WorldObject;

import java.util.Arrays;

import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;

public class ObjectManager {

    private final WorldObject[] worldObjects;

    public ObjectManager() {
        worldObjects = new WorldObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
        add(new LootObject(WeaponFactory.COMMON_AXE.create(), 27, 24));
        add(new LootObject(ShieldFactory.SHIELD_UNCOMMON.create(), 28, 24));
        add(new LootObject(ItemFactory.KEY.create(), 29, 24));
    }

    private void add(WorldObject gameObject) {
        for (int i = 0; i < worldObjects.length; i++) {
            if (worldObjects[i] == null) {
                worldObjects[i] = gameObject;
                return;
            }
        }
    }

    public WorldObject[] getWorldObjects() {
        return Arrays.copyOf(worldObjects, MAX_OBJECTS);
    }

    public void interact(int index) {
        var item = worldObjects[index];
        switch (item) {
            case null -> {
            }
            case LootObject loot -> {
                if (STASH.addToStash(loot.lootItem)) {
                    worldObjects[index] = null;
                }
            }
            case PlacedObject obj -> {
                // todo implement
            }
            default -> throw new RuntimeException("Unsupported world object type: " + item.getClass());
        }
    }
}
