package nl.rrx.object;

import java.util.Arrays;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;
import static nl.rrx.object.GameObjectType.BOOTS;
import static nl.rrx.object.GameObjectType.DOOR;

public class ObjectManager {

    private final GameObject[] gameObjects;

    public ObjectManager() {
        gameObjects = new GameObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
        gameObjects[0] = new GameObject(DOOR, 26, 16, 0, TILE_SIZE / 3);
        gameObjects[1] = new GameObject(DOOR, 28, 16, TILE_SIZE / 3, 0);
        gameObjects[2] = new GameObject(DOOR, 28, 17);
        gameObjects[3] = new GameObject(BOOTS, 26, 12);
    }

    public GameObject[] getGameObjects() {
        return Arrays.copyOf(gameObjects, MAX_OBJECTS);
    }

    public void removeObject(int index) {
        gameObjects[index] = null;
    }

    public GameObjectType getTypeFor(int index) {
        return gameObjects[index].type;
    }
}
