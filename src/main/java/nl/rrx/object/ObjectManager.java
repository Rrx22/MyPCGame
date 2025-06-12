package nl.rrx.object;

import nl.rrx.object.loot.LootObject;
import nl.rrx.object.loot.otherItems.ItemFactory;
import nl.rrx.object.loot.shield.ShieldFactory;
import nl.rrx.object.loot.weapon.WeaponFactory;
import nl.rrx.object.placed.PlacedObject;

import java.util.Arrays;

import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;

public class ObjectManager {

    // todo refactor to list / map / ...
    private final WorldObject[] worldObjects;

    public ObjectManager() {
        worldObjects = new WorldObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
        add(new LootObject(WeaponFactory.COMMON_AXE.create(), 27, 24));
        add(new LootObject(ShieldFactory.SHIELD_UNCOMMON.create(), 28, 24));
        add(new LootObject(ItemFactory.KEY.create(), 29, 24));
        add(new LootObject(ItemFactory.HEALTH_POTION.create(), 28, 25));
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
            case LootObject loot -> {
                if (STASH.addToStash(loot.lootItem)) {
                    worldObjects[index] = null;
                }
            }
            case PlacedObject placedObject -> placedObject.interact();
            default -> throw new RuntimeException("Unsupported world object type: " + item.getClass());
        }
    }

    public void remove(WorldObject worldObject) {
        for (int i = 0; i < worldObjects.length; i++) {
            if (worldObjects[i] == worldObject) {
                worldObjects[i] = null;
            }
        }
    }
}
