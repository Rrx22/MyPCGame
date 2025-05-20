package nl.rrx.sprite;

import nl.rrx.sound.SoundEffect;
import nl.rrx.util.PerformanceUtil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_MGR;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.CollisionUtil.NO_HIT;

public record AttackUtil(
        AttackType type,
        Rectangle attackArea,
        BufferedImage attackUp1,
        BufferedImage attackUp2,
        BufferedImage attackDown1,
        BufferedImage attackDown2,
        BufferedImage attackLeft1,
        BufferedImage attackLeft2,
        BufferedImage attackRight1,
        BufferedImage attackRight2
) {
    public static AttackUtil buildAndLoadImages(AttackType attackType, int width, int height, String fileStart) {
        return new AttackUtil(
                attackType,
                new Rectangle(0, 0, width, height),
                PerformanceUtil.getScaledImage(fileStart + "/atk-up-1.png", TILE_SIZE, TILE_SIZE * 2),
                PerformanceUtil.getScaledImage(fileStart + "/atk-up-2.png", TILE_SIZE, TILE_SIZE * 2),
                PerformanceUtil.getScaledImage(fileStart + "/atk-down-1.png", TILE_SIZE, TILE_SIZE * 2),
                PerformanceUtil.getScaledImage(fileStart + "/atk-down-2.png", TILE_SIZE, TILE_SIZE * 2),
                PerformanceUtil.getScaledImage(fileStart + "/atk-left-1.png", TILE_SIZE * 2, TILE_SIZE),
                PerformanceUtil.getScaledImage(fileStart + "/atk-left-2.png", TILE_SIZE * 2, TILE_SIZE),
                PerformanceUtil.getScaledImage(fileStart + "/atk-right-1.png", TILE_SIZE * 2, TILE_SIZE),
                PerformanceUtil.getScaledImage(fileStart + "/atk-right-2.png", TILE_SIZE * 2, TILE_SIZE)
        );
    }

    public void handleAttack(Sprite sprite) {
        switch (type) {
            case MELEE -> doMeleeAttack(sprite);
            case RANGED -> doRangedAttack(sprite);
        }
    }

    private void doMeleeAttack(Sprite sprite) {
        // save current info to reset after hit
        int currentWorldX = sprite.getWorldX();
        int currentWorldY = sprite.getWorldY();
        int collisionAreaWidth = sprite.getCollisionArea().width;
        int collisionAreaHeight = sprite.getCollisionArea().height;

        // adjust sprite's worldX/Y for attack area
        switch (sprite.getDirection()) {
            case UP -> sprite.setWorldY(sprite.getWorldY() - attackArea.height);
            case DOWN -> sprite.setWorldY(sprite.getWorldY() + attackArea.height);
            case LEFT -> sprite.setWorldX(sprite.getWorldX() - attackArea.width);
            case RIGHT -> sprite.setWorldX(sprite.getWorldX() + attackArea.width);
        }
        sprite.getCollisionArea().width = attackArea.width;
        sprite.getCollisionArea().height = attackArea.height;

        if (sprite instanceof Player player) {
            int monsterIdx = COLLISION_UTIL.checkSprite(PLAYER, MONSTER_MGR.getMonsters());
            if (monsterIdx != NO_HIT) {
                player.hit(MONSTER_MGR.get(monsterIdx));
                SOUND_MGR.playSoundEffect(SoundEffect.HIT_MONSTER);
            } else {
                SOUND_MGR.playSoundEffect(SoundEffect.SWING_WEAPON);
            }
        } else if (sprite instanceof NonPlayerSprite nps) {
            // do NPC / monster attack stuff ?
        }

        // restore original settings
        sprite.setWorldX(currentWorldX);
        sprite.setWorldY(currentWorldY);
        sprite.getCollisionArea().width = collisionAreaWidth;
        sprite.getCollisionArea().height = collisionAreaHeight;
    }

    private void doRangedAttack(Sprite sprite) {
        // todo implement
        System.out.println(sprite + " does a ranged attack!");
    }
}
