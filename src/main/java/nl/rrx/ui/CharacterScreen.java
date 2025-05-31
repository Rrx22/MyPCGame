package nl.rrx.ui;

import nl.rrx.object.GameObjectType;
import nl.rrx.util.PerformanceUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.util.ScreenUtil.getXForAlignToRightText;

public class CharacterScreen {

    private CharacterScreen() {
    }

    static void draw(Graphics2D g2, Font font) {
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
        textY = writeLine("Health", PLAYER.getHealthPoints() + "/" + PLAYER.getMaxHP(), g2, textX, textY, tailX);
        textY = writeLine("Strength", String.valueOf(PLAYER.strength), g2, textX, textY, tailX);
        textY = writeLine("Dexterity", String.valueOf(PLAYER.dexterity), g2, textX, textY, tailX);
        textY = writeLine("Magic", String.valueOf(PLAYER.magic), g2, textX, textY, tailX);
        textY = writeLine("Attack", String.valueOf(PLAYER.attack), g2, textX, textY, tailX);
        textY = writeLine("Defence", String.valueOf(PLAYER.defence), g2, textX, textY, tailX);
        textY = writeLine("Exp", String.valueOf(PLAYER.exp), g2, textX, textY, tailX);
        textY = writeLine("Next level", String.valueOf(PLAYER.expUntilNextLevel), g2, textX, textY, tailX);
        textY = writeLine("Coins", String.valueOf(PLAYER.coins), g2, textX, textY, tailX);
        // todo show correct images once I created them (fetch from player instead)
        textY = writeLineWithImg("Weapon", GameObjectType.HEART.imageUri, g2, textX, textY, tailX);
        textY = writeLineWithImg("Shield", GameObjectType.HEART.imageUri, g2, textX, textY, tailX);
    }

    private static int writeLine(String key, String value, Graphics2D g2, int textX, int textY, int tailX) {
        g2.drawString(key, textX, textY);
        g2.drawString(value, getXForAlignToRightText(g2, value, tailX), textY);
        return nextLine(textY);
    }

    private static int writeLineWithImg(String key, String uri, Graphics2D g2, int textX, int textY, int tailX) {
        var img = PerformanceUtil.getScaledImage(uri, TILE_SIZE, TILE_SIZE);
        int imgOffset = (TILE_SIZE * 2) / 3; // 2/3s of the tilesize
        g2.drawString(key, textX, textY);
        g2.drawImage(img, tailX - imgOffset, textY - imgOffset, null);
        return nextLine(textY);
    }

    private static int nextLine(int textY) {
        return textY + 35;
    }
}
