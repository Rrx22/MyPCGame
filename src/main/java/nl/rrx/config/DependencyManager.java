package nl.rrx.config;

import nl.rrx.event.EventManager;
import nl.rrx.object.ObjectManager;
import nl.rrx.sound.SoundHandler;
import nl.rrx.sprite.Player.Player;
import nl.rrx.sprite.Player.Stash;
import nl.rrx.sprite.nps.monster.MonsterManager;
import nl.rrx.sprite.nps.npc.NPCManager;
import nl.rrx.state.StateHandler;
import nl.rrx.tile.TileHandler;
import nl.rrx.ui.UI;
import nl.rrx.util.CollisionUtil;

public class DependencyManager {

    public static final Stash STASH = new Stash();
    public static final Player PLAYER = new Player();
    public static final UI UI = new UI();

    // Managers
    public static final ObjectManager OBJECT_MGR = new ObjectManager();
    public static final NPCManager NPC_MGR = new NPCManager();
    public static final MonsterManager MONSTER_MGR = new MonsterManager();
    public static final EventManager EVENT_MGR = new EventManager();

    // utils
    public static final CollisionUtil COLLISION_UTIL = new CollisionUtil();

    // handlers
    public static final KeyHandler KEY_HANDLER = new KeyHandler();
    public static final TileHandler TILE_HANDLER = new TileHandler();
    public static final SoundHandler SOUND_HANDLER = new SoundHandler();
    public static final StateHandler STATE_HANDLER = new StateHandler();

    private DependencyManager() {
        // no instantiation
    }
}
