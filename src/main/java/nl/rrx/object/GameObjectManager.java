package nl.rrx.object;

import nl.rrx.config.DependencyManager;

import java.awt.Graphics2D;

import static nl.rrx.config.settings.WorldSettings.MAX_OBJECTS;
import static nl.rrx.object.GameObjectType.*;

public class GameObjectManager {

    private final DependencyManager dm;
    public final GameObject[] gameObjects;

    public GameObjectManager(DependencyManager dm) {
        this.dm = dm;
        gameObjects = new GameObject[MAX_OBJECTS];
    }

    public void loadObjects() {
        gameObjects[0] = new GameObject(KEY, 39, 42);
        gameObjects[1] = new GameObject(KEY, 10, 30);
        gameObjects[2] = new GameObject(KEY, 4, 2);
        gameObjects[3] = new GameObject(DOOR, 27, 12);
        gameObjects[4] = new GameObject(DOOR, 32, 4);
        gameObjects[5] = new GameObject(DOOR, 39, 8);
        gameObjects[6] = new GameObject(CHEST, 39, 6);
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < MAX_OBJECTS; i++) {
            if (gameObjects[i] != null) {
                gameObjects[i].draw(g2, dm.player);
            }
        }
    }

}
