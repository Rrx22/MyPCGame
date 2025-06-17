package nl.rrx.object;

import nl.rrx.object.loot.LootObject;
import nl.rrx.object.loot.otherItems.ItemFactory;
import nl.rrx.object.loot.shield.ShieldFactory;
import nl.rrx.object.loot.weapon.WeaponFactory;
import nl.rrx.object.placed.PlacedObject;
import nl.rrx.object.placed.PlacedObjectFactory;

import java.util.ArrayList;
import java.util.List;

import static nl.rrx.config.DependencyManager.STASH;

public class ObjectManager {

    private final List<WorldObject> worldObjects = new ArrayList<>();

    public ObjectManager() {
        loadObjects();
    }

    private void loadObjects() {
        worldObjects.add(new LootObject(WeaponFactory.COMMON_AXE.create(), 27, 24));
        worldObjects.add(new LootObject(ShieldFactory.SHIELD_UNCOMMON.create(), 28, 24));
        worldObjects.add(new LootObject(ItemFactory.KEY.create(), 29, 24));
        worldObjects.add(new LootObject(ItemFactory.HEALTH_POTION_S.create(), 32, 25));
        worldObjects.add(new LootObject(ItemFactory.HEALTH_POTION_M.create(), 33, 25));
        worldObjects.add(new LootObject(ItemFactory.HEALTH_POTION_L.create(), 34, 25));
        worldObjects.add(PlacedObjectFactory.DOOR.create(12, 16));
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public void interact(WorldObject worldObject) {
        switch (worldObject) {
            case LootObject loot -> STASH.addToStash(loot);
            case PlacedObject placedObject -> placedObject.interact();
            default -> throw new RuntimeException("Unsupported world object type: " + worldObject.getClass());
        }
    }

    public void remove(WorldObject worldObject) {
        worldObjects.remove(worldObject);
    }
}
