package nl.rrx.common;

import nl.rrx.object.GameObject;
import nl.rrx.sprite.npc.NPC;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.OBJECT_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;

public interface DrawOrderMatters {
    void draw(Graphics2D g2);
    int getWorldY();

    static void drawSpritesAndObjectsInOrder(Graphics2D g2) {
        List<DrawOrderMatters> objectsAndSprites = new ArrayList<>();
        objectsAndSprites.add(PLAYER);
        for (NPC npc : NPC_MGR.getNPCs()) {
            if (npc != null) objectsAndSprites.add(npc);
        }
        for (GameObject obj : OBJECT_MGR.getGameObjects()) {
            if (obj != null) objectsAndSprites.add(obj);
        }
        objectsAndSprites.stream()
                .sorted(Comparator.comparingInt(DrawOrderMatters::getWorldY))
                .forEach(o -> o.draw(g2));
    }
}
