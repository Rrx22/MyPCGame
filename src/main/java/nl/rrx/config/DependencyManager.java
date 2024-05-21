package nl.rrx.config;

import nl.rrx.object.ObjectManager;
import nl.rrx.sound.Sound;
import nl.rrx.sprite.Player;
import nl.rrx.tile.TileManager;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public final Player player;

    public final KeyHandler keyHandler;

    public final TileManager tileManager;

    public final CollisionUtil collisionUtil;

    public final ObjectManager objectManager;

    public final Sound sound;

    public DependencyManager() {
        keyHandler = new KeyHandler();
        tileManager = new TileManager(this);
        player = new Player(this);
        collisionUtil = new CollisionUtil(this);
        objectManager = new ObjectManager(this);
        sound = new Sound();
    }
}
