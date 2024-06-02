package nl.rrx.object;

import nl.rrx.sprite.Player;

import java.awt.Graphics2D;
import java.util.Arrays;

import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;
import static nl.rrx.object.GameObjectType.*;

public class ObjectManager {

    private final Player player;
    private final GameObject[] gameObjects;

    public ObjectManager(Player player) {
        this.player = player;
        gameObjects = new GameObject[MAX_OBJECTS];
        loadObjects();
    }

    private void loadObjects() {
        gameObjects[0] = new GameObject(KEY, 28, 7);
        gameObjects[1] = new GameObject(KEY, 14, 43);
        gameObjects[2] = new GameObject(KEY, 38, 36);
        gameObjects[3] = new GameObject(DOOR, 19, 26);
        gameObjects[4] = new GameObject(DOOR, 10, 23);
        gameObjects[5] = new GameObject(DOOR, 12, 16);
        gameObjects[6] = new GameObject(CHEST, 14, 15);
        gameObjects[7] = new GameObject(BOOTS, 19, 37);
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < MAX_OBJECTS; i++) {
            if (gameObjects[i] != null) {
                gameObjects[i].draw(g2, player);
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
