package nl.rrx.config;

import nl.rrx.sprite.Player;
import nl.rrx.tile.TileManager;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public final Player player;

    public final KeyHandler keyHandler;

    public final TileManager tileManager;

    public final CollisionUtil collisionUtil;

    public DependencyManager() {
        keyHandler = new KeyHandler();
        tileManager = new TileManager(this);
        player = new Player(this);
        collisionUtil = new CollisionUtil(this);
    }
}
