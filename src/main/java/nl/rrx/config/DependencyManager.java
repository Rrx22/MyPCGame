package nl.rrx.config;

import nl.rrx.object.ObjectManager;
import nl.rrx.sound.SoundManager;
import nl.rrx.sprite.Player;
import nl.rrx.sprite.npc.NPCManager;
import nl.rrx.state.StateManager;
import nl.rrx.tile.TileManager;
import nl.rrx.ui.UI;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public final Player player;

    public final StateManager stateManager;

    public final SoundManager soundManager;

    public final TileManager tileManager;

    public final ObjectManager objectManager;

    public final CollisionUtil collisionUtil;

    public final NPCManager npcManager;

    public final KeyHandler keyHandler;

    public final UI ui;

    public DependencyManager() {
        player = new Player(this);
        stateManager = new StateManager();
        soundManager = new SoundManager();
        tileManager = new TileManager(player);
        objectManager = new ObjectManager(player);
        collisionUtil = new CollisionUtil(tileManager, objectManager);
        npcManager = new NPCManager(this);
        keyHandler = new KeyHandler(this);
        ui = new UI(this);
    }
}
