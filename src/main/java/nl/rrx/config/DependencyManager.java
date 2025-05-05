package nl.rrx.config;

import nl.rrx.event.EventHandler;
import nl.rrx.object.ObjectManager;
import nl.rrx.sound.SoundManager;
import nl.rrx.sprite.Player;
import nl.rrx.sprite.npc.NPCManager;
import nl.rrx.state.StateManager;
import nl.rrx.tile.TileManager;
import nl.rrx.ui.UI;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public static final Player PLAYER = new Player();
    public static final UI UI = new UI();

    // Managers
    public static final StateManager STATE_MGR = new StateManager();
    public static final SoundManager SOUND_MGR = new SoundManager();
    public static final TileManager TILE_MGR = new TileManager();
    public static final ObjectManager OBJECT_MGR = new ObjectManager();
    public static final NPCManager NPC_MGR = new NPCManager();

    // utils
    public static final CollisionUtil COLLISION_UTIL = new CollisionUtil();

    // handlers
    public static final KeyHandler KEY_HANDLER = new KeyHandler();
    public static final EventHandler EVENT_HANDLER = new EventHandler();

    private DependencyManager() {
        // no instantiation
    }
}
