package nl.rrx.sprite.npc;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.settings.WorldSettings;

import java.awt.Graphics2D;
import java.util.Arrays;


public class NPCManager {

    private final DependencyManager dm;
    private NPC[] npcs = new NPC[WorldSettings.MAX_NPCS];

    public NPCManager(DependencyManager dm) {
        this.dm = dm;
        this.loadNPCs();
    }

    public void loadNPCs() {
        Wizard wizard = new Wizard(dm, 16, 12);
        this.npcs[0] = wizard;
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
}
