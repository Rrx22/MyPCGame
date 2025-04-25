package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.settings.WorldSettings;

import java.awt.Graphics2D;
import java.util.Arrays;


public class NPCManager {

    private final DependencyManager dm;
    private final NPC[] npcs = new NPC[WorldSettings.MAX_NPCS];

    public NPCManager(DependencyManager dm) {
        this.dm = dm;
        this.loadNPCs();
    }

    public void loadNPCs() {
        this.npcs[0] = new Wizard(dm, 16, 12);
        this.npcs[1] = new Rogue(dm, 15, 15);
        this.npcs[2] = new Knight(dm, 9, 15);
        this.npcs[3] = new Giant(dm, 9, 23);
        this.npcs[4] = new Wizard(dm, 15, 17);
        this.npcs[5] = new Wizard(dm, 15, 16);
        this.npcs[6] = new Knight(dm, 19, 15);
        this.npcs[7] = new Knight(dm, 18, 15);
        this.npcs[8] = new Rogue(dm, 17, 15);
        this.npcs[9] = new Rogue(dm, 16, 15);
    }

    public void updateNPCs() {
        for (NPC npc : npcs) {
            if (npc != null) {
                npc.update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (NPC npc : npcs) {
            if (npc != null) {
                npc.draw(g2);
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
