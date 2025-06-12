package nl.rrx.common;

import nl.rrx.sprite.nps.monster.Monster;
import nl.rrx.sprite.nps.npc.NPC;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;

public interface SortedDrawable {
    void draw(Graphics2D g2);

    /**
     * This is what determines the drawing order
     */
    int getWorldY();

    static void drawSpritesAndObjectsInOrder(Graphics2D g2) {
        List<SortedDrawable> objectsAndSprites = new ArrayList<>();
        objectsAndSprites.add(PLAYER);
        for (NPC npc : NPC_MGR.getNPCs()) {
            if (npc != null) objectsAndSprites.add(npc);
        }
        objectsAndSprites.addAll(OBJECT_MGR.getWorldObjects());
        for (Monster monster : MONSTER_MGR.getMonsters()) {
            if (monster != null) objectsAndSprites.add(monster);
        }
        objectsAndSprites.stream()
                .sorted(Comparator.comparingInt(SortedDrawable::getWorldY))
                .forEach(o -> o.draw(g2));
    }
}
