package nl.rrx.sprite;

import nl.rrx.sprite.Player.Player;
import nl.rrx.sprite.nps.NonPlayerSprite;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.COLLISION_UTIL;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.CollisionUtil.NO_HIT;
import static nl.rrx.util.ScreenUtil.getScreenX;
import static nl.rrx.util.ScreenUtil.getScreenY;

public record AttackUtil(
        AttackType attackType,
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
        if (attackType.isCloseRange()) {
            doCloseRangeAttack(sprite);
        }
    }

    private void doCloseRangeAttack(Sprite sprite) {
        // save current info to reset after hit
        int currentWorldX = sprite.getWorldX();
        int currentWorldY = sprite.getWorldY();
        int collisionAreaWidth = sprite.getCollisionArea().width;
        int collisionAreaHeight = sprite.getCollisionArea().height;

        // adjust sprite's worldX/Y for attack area
        int[] newWorldXY = computeAttackWorldXY(sprite);
        sprite.setWorldX(newWorldXY[0]);
        sprite.setWorldY(newWorldXY[1]);
        sprite.getCollisionArea().width = attackArea.width;
        sprite.getCollisionArea().height = attackArea.height;

        // attack
        if (sprite instanceof Player player) {
            int monsterIdx = COLLISION_UTIL.checkSprite(PLAYER, MONSTER_MGR.getMonsters());
            if (monsterIdx != NO_HIT) {
                player.hit(MONSTER_MGR.get(monsterIdx));
                SOUND_HANDLER.playSoundEffect(attackType.hitSound);
            } else {
                SOUND_HANDLER.playSoundEffect(attackType.missSound);
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

    public void drawIfDebug(Graphics2D g2, Sprite sprite, Color color) {
        if (attackType.isCloseRange()) {
            int[] worldXY = computeAttackWorldXY(sprite);
            int worldX = worldXY[0];
            int worldY = worldXY[1];
            int screenX = getScreenX(worldX);
            int screenY = getScreenY(worldY);
            var atkCollisionArea = new Rectangle(sprite.getCollisionArea().x, sprite.getCollisionArea().y, attackArea.width, attackArea.height);
            COLLISION_UTIL.drawIfDebug(g2, color, screenX, screenY, atkCollisionArea);
        }
    }

    private int[] computeAttackWorldXY(Sprite sprite) {
        int worldX = sprite.getWorldX();
        int worldY = sprite.getWorldY();
        switch (sprite.getDirection()) {
            case UP -> {
                worldX -= attackArea.width / 4;
                worldY -= TILE_SIZE;
            }
            case DOWN -> {
                worldX -= attackArea.width / 4;
                worldY += TILE_SIZE - attackArea.height / 2;
            }
            case LEFT -> worldX -= TILE_SIZE - attackArea.width / 4;
            case RIGHT -> worldX += TILE_SIZE - attackArea.width / 4;
        }
        return new int[]{worldX, worldY};
    }
}
