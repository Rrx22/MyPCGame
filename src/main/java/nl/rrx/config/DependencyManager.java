package nl.rrx.config;

import nl.rrx.object.ObjectManager;
import nl.rrx.sound.SoundManager;
import nl.rrx.sprite.Player;
import nl.rrx.sprite.npc.NPCManager;
import nl.rrx.tile.TileManager;
import nl.rrx.ui.UI;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public final Player player;

    public final KeyHandler keyHandler;

    public final SoundManager soundManager;

    public final TileManager tileManager;

    public final ObjectManager objectManager;

    public final NPCManager npcManager;

    public final CollisionUtil collisionUtil;

    public final UI ui;

    public DependencyManager() {
        player = new Player(this);
        keyHandler = new KeyHandler();
        soundManager = new SoundManager();
        tileManager = new TileManager(player);
        objectManager = new ObjectManager(player);
        npcManager = new NPCManager(this);
        collisionUtil = new CollisionUtil(tileManager, objectManager);
        ui = new UI(this);
    }
}
