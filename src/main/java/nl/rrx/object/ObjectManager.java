package nl.rrx.object;

import java.awt.Graphics2D;
import java.util.Arrays;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;

public class ObjectManager {

    private final GameObject[] gameObjects;

    public ObjectManager() {
        gameObjects = new GameObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
//        gameObjects[7] = new GameObject(BOOTS, 19, 37);
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < MAX_OBJECTS; i++) {
            if (gameObjects[i] != null) {
                gameObjects[i].draw(g2, PLAYER);
            }
        }
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
