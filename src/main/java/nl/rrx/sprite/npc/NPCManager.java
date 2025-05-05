package nl.rrx.sprite.npc;

import nl.rrx.config.settings.WorldSettings;

import java.util.Arrays;


public class NPCManager {

    private final NPC[] npcs = new NPC[WorldSettings.MAX_NPCS];

    public NPCManager() {
        this.loadNPCs();
    }

    public void loadNPCs() {
        this.npcs[0] = new Wizard(16, 12);
        this.npcs[1] = new Rogue(15, 15);
        this.npcs[2] = new Knight(9, 15);
        this.npcs[3] = new Giant(9, 23);
        this.npcs[4] = new Wizard(15, 17);
        this.npcs[5] = new Wizard(15, 16);
        this.npcs[6] = new Knight(19, 15);
        this.npcs[7] = new Knight(18, 15);
        this.npcs[8] = new Rogue(17, 15);
        this.npcs[9] = new Rogue(16, 15);
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
