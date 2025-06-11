package nl.rrx.object.loot.otherItems;

import nl.rrx.object.loot.Item;
import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum ItemFactory {
    KEY("key.png", "Key", "Unlocks a door."),
    BOOTS("boots.png", "Boots", "To wear on your feet."),
    HEALTH_POTION("health_potion.png", "Health potion", "Consume this potion to", "restore some health."),
    MANA_POTION("mana_potion.png", "Mana potion", "Consume this potion to", "restore some mana."),
    ;

    private final BufferedImage image;
    private final String title;
    private final String[] description;

    ItemFactory(String imageUri, String title, String... description) {
        this.image = PerformanceUtil.getScaledImage("/images/objects/" + imageUri, TILE_SIZE, TILE_SIZE);
        this.title = title;
        this.description = description;
    }

    public Item create() {
        return switch (this) {
            case KEY -> new OBJ_Key(image, title, description);
            case BOOTS -> new OBJ_Boots(image, title, description);
            case HEALTH_POTION -> new OBJ_HealthPotion(image, title, description);
            case MANA_POTION -> null;
        };
    }
}
