package nl.rrx.ui;

import nl.rrx.object.Stashable;
import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Player.Stash;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForAlignToRightText;

public class CharacterScreen {

    private static final int STASH_MAX_ROW = 3;
    private static final int STASH_MAX_COL = 4;
    private static int CURSOR_ROW = 0;
    private static int CURSOR_COL = 0;

    private CharacterScreen() {
    }

    static void draw(Graphics2D g2, Font font) {
        drawStatsFrame(g2, font);
        drawStashFrame(g2);
    }

    private static void drawStatsFrame(Graphics2D g2, Font font) {
        // create frame
        final int frameX = TILE_SIZE;
        final int frameY = TILE_SIZE;
        final int frameWidth = TILE_SIZE * 5;
        final int frameHeight = TILE_SIZE * 10;
        UIUtil.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);

        // text
        int textX = frameX + TILE_SIZE / 2;
        int textY = frameY + TILE_SIZE;
        int tailX = (frameX + frameWidth) - 30;
        g2.setColor(Color.white);
        g2.setFont(font);

        textY = writeLine("Level", String.valueOf(PLAYER.level), g2, textX, textY, tailX);
        textY = writeSkillLine("Health", PLAYER.getHealthPoints() + "/" + PLAYER.getMaxHP(), g2, textX, textY, tailX);
        textY = writeSkillLine("Strength", String.valueOf(PLAYER.strength), g2, textX, textY, tailX);
        textY = writeSkillLine("Dexterity", String.valueOf(PLAYER.dexterity), g2, textX, textY, tailX);
        textY = writeSkillLine("Magic", String.valueOf(PLAYER.magic), g2, textX, textY, tailX);
        textY = writeLine("Attack", String.valueOf(PLAYER.getAttack()), g2, textX, textY, tailX);
        textY = writeLine("Defence", String.valueOf(PLAYER.getDefence()), g2, textX, textY, tailX);
        textY = writeLine("XP", PLAYER.exp + "xp", g2, textX, textY, tailX);
        textY = writeLine("Next level", PLAYER.expUntilNextLevel + "xp", g2, textX, textY, tailX);
        textY = writeLine("Coins", String.valueOf(PLAYER.coins), g2, textX, textY, tailX);
        textY = writeLineWithImg("Weapon", PLAYER.weapon.image, g2, textX, textY, tailX);
        writeLineWithImg("Shield", PLAYER.shield.image, g2, textX, textY, tailX);
    }

    private static void drawStashFrame(Graphics2D g2) {
        // create frame
        final int frameX = TILE_SIZE * 9;
        final int frameY = TILE_SIZE;
        final int frameWidth = TILE_SIZE * 6;
        final int frameHeight = TILE_SIZE * 5;
        UIUtil.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight);

        // item slots
        final int startSlotX = frameX + 20;
        final int startSlotY = frameY + 20;
        final int slotSize = TILE_SIZE + 3;
        int slotX = startSlotX;
        int slotY = startSlotY;

        // player's stashed items
        var items = STASH.items();
        for (int i = 0; i < Stash.MAX; i++) {
            Stashable item = items[i];
            if (item != null) {
                g2.drawImage(item.image(), slotX, slotY, null);
                slotX += slotSize;
                if (i == 4 || i == 9 || i == 14) {
                    slotX = startSlotX;
                    slotY += slotSize;
                }
            }
        }

        // cursor
        int cursorX = startSlotX + (slotSize * CURSOR_COL);
        int cursorY = startSlotY + (slotSize * CURSOR_ROW);
        int cursorSize = TILE_SIZE;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorSize, cursorSize, 10, 10);
    }

    private static int writeLine(String key, String value, Graphics2D g2, int textX, int textY, int tailX) {
        g2.drawString(key, textX, textY);
        g2.drawString(value, getXForAlignToRightText(g2, value, tailX), textY);
        return nextLine(textY);
    }

    private static int writeSkillLine(String key, String value, Graphics2D g2, int textX, int textY, int tailX) {
        if (PLAYER.skillPoints > 0) {
            value = "+ " + value;
        }
        g2.drawString(key, textX, textY);
        g2.drawString(value, getXForAlignToRightText(g2, value, tailX), textY);
        return nextLine(textY);
    }

    private static int writeLineWithImg(String key, BufferedImage img, Graphics2D g2, int textX, int textY, int tailX) {
        int imgOffset = (TILE_SIZE * 2) / 3; // 2/3s of the tilesize
        g2.drawString(key, textX, textY);
        g2.drawImage(img, tailX - imgOffset, textY - imgOffset, null);
        return nextLine(textY);
    }

    private static int nextLine(int textY) {
        return textY + 35;
    }

    public static void moveCursor(Direction direction) {
        if ((Direction.UP.equals(direction) && CURSOR_ROW != 0)
         || (Direction.DOWN.equals(direction) && CURSOR_ROW < STASH_MAX_ROW)
         || (Direction.LEFT.equals(direction) && CURSOR_COL != 0)
         || (Direction.RIGHT.equals(direction) && CURSOR_COL < STASH_MAX_COL)) {
            CURSOR_COL += direction.moveX(1);
            CURSOR_ROW += direction.moveY(1);
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
        }
    }
}
