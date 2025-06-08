package nl.rrx.ui.characterScreen;

import nl.rrx.sound.SoundEffect;
import nl.rrx.sprite.Direction;
import nl.rrx.ui.UIUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.SOUND_HANDLER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForAlignToRightText;

class StatsFrame implements Interactable {

    private int cursor = 0;

    @Override
    public boolean moveCursor(Direction direction) {
        int maxRow = 11;
        if ((Direction.UP.equals(direction) && cursor != 0)
                || (Direction.DOWN.equals(direction) && cursor < maxRow)) {
            cursor += direction.moveY(1);
            SOUND_HANDLER.playSoundEffect(SoundEffect.CURSOR);
            return true;
        }
        return false;
    }

    @Override
    public void doAction() {
        if (PLAYER.skillPoints > 0) {
            if (cursor == 1) {
                PLAYER.skillMaxHp();
            } else if (cursor == 2) {
                PLAYER.strength++;
                PLAYER.skillPoints--;
            } else if (cursor == 3) {
                PLAYER.dexterity++;
                PLAYER.skillPoints--;
            } else if (cursor == 4) {
                PLAYER.magic++;
                PLAYER.skillPoints--;
            } else if (cursor == 9) {
                System.out.println("todo show weapon stats");
            } else if (cursor == 10) {
                System.out.println("todo show shield stats");

            }
        }

    }

    void draw(Graphics2D g2, Font font, boolean hasFocus) {
        // create frame
        final int frameX = TILE_SIZE;
        final int frameY = TILE_SIZE;
        final int frameWidth = TILE_SIZE * 5;
        final int frameHeight = TILE_SIZE * 10;
        UIUtil.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight, hasFocus);

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
        textY = writeLine("Attack", String.format("%s-%s", PLAYER.strength + PLAYER.weapon.minAttack, PLAYER.strength + PLAYER.weapon.maxAttack), g2, textX, textY, tailX);
        textY = writeLine("Defence", String.valueOf(PLAYER.getDefence()), g2, textX, textY, tailX);
        textY = writeLine("XP", PLAYER.exp + "xp", g2, textX, textY, tailX);
        textY = writeLine("Next level", PLAYER.expUntilNextLevel + "xp", g2, textX, textY, tailX);
        textY = writeLine("Coins", String.valueOf(PLAYER.coins), g2, textX, textY, tailX);
        textY = writeLineWithImg("Weapon", PLAYER.weapon.image, g2, textX, textY, tailX);
        writeLineWithImg("Shield", PLAYER.shield.image, g2, textX, textY, tailX);

        // cursor
        int cursorX = textX - TILE_SIZE / 4;
        int cursorY = frameY + TILE_SIZE + (35 * cursor) - font.getSize() - 8;
        int cursorWidth = frameWidth - TILE_SIZE / 2;
        int cursorHeight = TILE_SIZE - 8;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    private int writeLine(String key, String value, Graphics2D g2, int textX, int textY, int tailX) {
        g2.drawString(key, textX, textY);
        g2.drawString(value, getXForAlignToRightText(g2, value, tailX), textY);
        return nextLine(textY);
    }

    private int writeSkillLine(String key, String value, Graphics2D g2, int textX, int textY, int tailX) {
        if (PLAYER.skillPoints > 0) {
            value = "+ " + value;
        }
        g2.drawString(key, textX, textY);
        g2.drawString(value, getXForAlignToRightText(g2, value, tailX), textY);
        return nextLine(textY);
    }

    private int writeLineWithImg(String key, BufferedImage img, Graphics2D g2, int textX, int textY, int tailX) {
        int imgOffset = (TILE_SIZE * 2) / 3; // 2/3s of the tilesize
        g2.drawString(key, textX, textY);
        g2.drawImage(img, tailX - imgOffset, textY - imgOffset, null);
        return nextLine(textY);
    }

    private int nextLine(int textY) {
        return textY + 35;
    }
}
