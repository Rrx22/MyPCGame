package nl.rrx.object.loot.otherItems;

import nl.rrx.object.loot.Item;
import nl.rrx.util.PerformanceUtil;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public enum ItemFactory {
    KEY("key.png", OBJ_Key.class),
    BOOTS("boots.png", OBJ_Boots.class),
    HEALTH_POTION_S("pot-hp-s.png", OBJ_HealthPotionS.class),
    HEALTH_POTION_M("pot-hp-m.png", OBJ_HealthPotionM.class),
    HEALTH_POTION_L("pot-hp-l.png", OBJ_HealthPotionL.class),
    ;

    private final BufferedImage image;
    private final Class<? extends Item> clazz;

    ItemFactory(String imageUri, Class<? extends Item> clazz) {
        this.image = PerformanceUtil.getScaledImage("/images/objects/" + imageUri, TILE_SIZE, TILE_SIZE);
        this.clazz = clazz;
    }

    public Item create() {
        try {
            Constructor<? extends Item> ctor = clazz.getConstructor(BufferedImage.class);
            return ctor.newInstance(image);
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate item: " + clazz);
        }
    }
}
