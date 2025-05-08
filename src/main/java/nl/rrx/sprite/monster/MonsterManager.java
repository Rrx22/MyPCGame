package nl.rrx.sprite.monster;

import nl.rrx.config.settings.WorldSettings;

import java.util.Arrays;


public class MonsterManager {

    private final Monster[] monsters = new Monster[WorldSettings.MAX_MONSTERS];

    public MonsterManager() {
        this.loadMonsters();
    }

    public void loadMonsters() {
        this.monsters[0] = new GreenSlime(26, 17);
    }

    public void updateMonsters() {
        for (Monster monster : monsters) {
            if (monster != null) {
                monster.update();
            }
        }
    }

    public Monster[] getNPCs() {
        return Arrays.copyOf(monsters, WorldSettings.MAX_NPCS);
    }

    public Monster get(int idx) {
        return monsters[idx];
    }

    public void remove(int idx) {
        monsters[idx] = null;
    }
}
