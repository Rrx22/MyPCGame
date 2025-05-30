package nl.rrx.sprite.monster;

import nl.rrx.config.settings.WorldSettings;

import java.util.Arrays;


public class MonsterManager {

    private final Monster[] monsters = new Monster[WorldSettings.MAX_MONSTERS];

    public MonsterManager() {
        this.loadMonsters();
    }

    public void loadMonsters() {
        this.monsters[0] = new GreenSlime(11, 33);
        this.monsters[1] = new GreenSlime(15, 37);
        this.monsters[2] = new GreenSlime(13, 39);
    }

    public void updateMonsters() {
        for (Monster monster : monsters) {
            if (monster == null) continue;
            if (monster.isAlive() && !monster.isDying()) monster.update();
            else if (!monster.isAlive()) remove(monster);
        }
    }

    public Monster[] getMonsters() {
        return Arrays.copyOf(monsters, WorldSettings.MAX_NPCS);
    }

    public Monster get(int idx) {
        return monsters[idx];
    }

    public void remove(Monster monster) {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] == monster) {
                monsters[i] = null;
            }
        }
    }
}
