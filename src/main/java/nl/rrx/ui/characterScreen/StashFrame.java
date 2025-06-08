package nl.rrx.ui.characterScreen;

import nl.rrx.object.Stashable;
import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Player.Stash;
import nl.rrx.ui.UIUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.STASH;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

class StashFrame implements Interactable {

    private int cursorRow = 0;
    private int cursorCol = 0;

    @Override
    public boolean moveCursor(Direction direction) {
        int maxRow = 3;
        int maxCol = 4;
        if ((Direction.UP.equals(direction) && cursorRow != 0)
                || (Direction.DOWN.equals(direction) && cursorRow < maxRow)
                || (Direction.LEFT.equals(direction) && cursorCol != 0)
                || (Direction.RIGHT.equals(direction) && cursorCol < maxCol)) {
            cursorCol += direction.moveX(1);
            cursorRow += direction.moveY(1);
            return true;
        }
        return false;
    }

    @Override
    public void doAction() {

    }

    void draw(Graphics2D g2, boolean hasFocus) {
        // create frame
        int frameX = TILE_SIZE * 9;
        int frameY = TILE_SIZE;
        int frameWidth = TILE_SIZE * 6;
        int frameHeight = TILE_SIZE * 5;
        UIUtil.drawSubWindow(g2, frameX, frameY, frameWidth, frameHeight, hasFocus);

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
        int cursorX = startSlotX + (slotSize * cursorCol);
        int cursorY = startSlotY + (slotSize * cursorRow);
        int cursorSize = TILE_SIZE;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorSize, cursorSize, 10, 10);
    }
}
