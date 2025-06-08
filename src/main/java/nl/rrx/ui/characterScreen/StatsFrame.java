package nl.rrx.ui.characterScreen;

import nl.rrx.sprite.Direction;
import nl.rrx.ui.UIUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForAlignToRightText;

class StatsFrame implements Interactable {

    @Override
    public void moveCursor(Direction direction) {
        // todo implement
    }

    void draw(Graphics2D g2, Font font) {
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
        textY = writeLine("Attack", String.format("%s-%s", PLAYER.strength + PLAYER.weapon.minAttack, PLAYER.strength + PLAYER.weapon.maxAttack), g2, textX, textY, tailX);
        textY = writeLine("Defence", String.valueOf(PLAYER.getDefence()), g2, textX, textY, tailX);
        textY = writeLine("XP", PLAYER.exp + "xp", g2, textX, textY, tailX);
        textY = writeLine("Next level", PLAYER.expUntilNextLevel + "xp", g2, textX, textY, tailX);
        textY = writeLine("Coins", String.valueOf(PLAYER.coins), g2, textX, textY, tailX);
        textY = writeLineWithImg("Weapon", PLAYER.weapon.image, g2, textX, textY, tailX);
        writeLineWithImg("Shield", PLAYER.shield.image, g2, textX, textY, tailX);
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
