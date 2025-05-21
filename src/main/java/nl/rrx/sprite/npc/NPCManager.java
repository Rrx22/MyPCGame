package nl.rrx.sprite.npc;

import nl.rrx.config.settings.WorldSettings;

import java.util.Arrays;


public class NPCManager {

    private final NPC[] npcs = new NPC[WorldSettings.MAX_NPCS];

    public NPCManager() {
        this.loadNPCs();
    }

    public void loadNPCs() {
        this.npcs[0] = new Wizard(26, 30);
        this.npcs[1] = new Rogue(30, 28);
        this.npcs[2] = new Knight(32, 33);
        this.npcs[3] = new Giant(11, 17);
    }

    public void updateNPCs() {
        for (NPC npc : npcs) {
            if (npc != null) {
                npc.update();
            }
        }
    }

    public NPC[] getNPCs() {
        return Arrays.copyOf(npcs, WorldSettings.MAX_NPCS);
    }

    public NPC get(int idx) {
        return npcs[idx];
    }

    public void remove(int idx) {
        npcs[idx] = null;
    }
}
