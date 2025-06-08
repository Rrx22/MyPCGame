package nl.rrx.object;

import java.util.Arrays;

import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;

public class ObjectManager {

    private final GameObject[] gameObjects;

    public ObjectManager() {
        gameObjects = new GameObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
//        gameObjects[0] = new GameObject(DOOR, 30, 17, 0, TILE_SIZE / 3);
//        gameObjects[1] = new GameObject(DOOR, 31, 15, TILE_SIZE / 2, 0);
//        gameObjects[2] = new GameObject(CHEST, 30, 40, TILE_SIZE, TILE_SIZE);
//        gameObjects[3] = new GameObject(BOOTS, 26, 12);
    }

    private void add(GameObject gameObject) {
        for (int i = 0; i < gameObjects.length; i++) {
            if (gameObjects[i] == null) {
                gameObjects[i] = gameObject;
                return;
            }
        }
    }

    public GameObject[] getGameObjects() {
        return Arrays.copyOf(gameObjects, MAX_OBJECTS);
    }

    public void handlePlayerInteraction(int index) {
        var item = gameObjects[index];
        switch (item.type) {
            case KEY -> {
                if (STASH.addToStash(item)) {
                    gameObjects[index] = null;
                } else {
                }
            }
        }
    }
}
