package nl.rrx.ui;

import nl.rrx.sprite.Direction;
import nl.rrx.sprite.Player.Player;
import nl.rrx.sprite.Sprite;
import nl.rrx.util.ScreenUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;

public class FloatingBattleMessages {

    private FloatingBattleMessages() {
    }

    private static final List<BattleMessage> FLOATING_BATTLE_MESSAGES = new ArrayList<>();
    private static final int MAX_FRAMES = 150;

    public static void draw(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, TILE_SIZE / 3));
        for (int i = 0; i < FLOATING_BATTLE_MESSAGES.size(); i++) {
            BattleMessage bm = FLOATING_BATTLE_MESSAGES.get(i);
            bm.swerve();

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bm.transparency()));
            g2.setColor(Color.black);
            g2.drawString(bm.message, bm.screenX + 2, bm.screenY + 2); // shadow
            g2.setColor(bm.color());
            g2.drawString(bm.message, bm.screenX, bm.screenY);

            if (bm.counter >= MAX_FRAMES) {
                FLOATING_BATTLE_MESSAGES.remove(bm);
            }
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public static void add(Sprite sprite, String message, MessageType type) {
        int screenX = determineScreenX(sprite);
        int screenY = determineScreenY(sprite);
        FLOATING_BATTLE_MESSAGES.add(new BattleMessage(message, type, screenX, screenY));
    }

    private static int determineScreenY(Sprite sprite) {
        var spriteScreenY = ScreenUtil.getScreenY(sprite.getWorldY());
        if (sprite instanceof Player) {
            return spriteScreenY;
        }
        return PLAYER.getDirection() == Direction.DOWN
                ? spriteScreenY + TILE_SIZE
                : spriteScreenY;
    }

    private static int determineScreenX(Sprite sprite) {
        int spriteScreenX = ScreenUtil.getScreenX(sprite.getWorldX());
        if (sprite instanceof Player) {
            return spriteScreenX + TILE_SIZE / 2;
        }
        return spriteScreenX + switch (PLAYER.getDirection()) {
            case UP, LEFT -> TILE_SIZE / 2;
            case RIGHT -> TILE_SIZE;
            case DOWN -> 0;
        };
    }

    private static class BattleMessage {
        public final String message;
        public final MessageType type;
        public int screenX;
        public int screenY;
        public int counter = 0;
        private boolean swerveLeft;

        BattleMessage(String message, MessageType type, int screenX, int screenY) {
            this.message = message;
            this.screenX = screenX;
            this.screenY = screenY;
            this.type = type;
            this.swerveLeft = type.reverseSwerve();
        }

        Color color() {
            return type.color;
        }

        float transparency() {
            return (float) (MAX_FRAMES - counter) / MAX_FRAMES;
        }

        void swerve() {
            counter++;
            screenY--;
            if (swerveLeft) {
                screenX--;
            } else {
                screenX++;
            }
            if (counter % 25 == 0) {
                swerveLeft = !swerveLeft;
            }
        }
    }

    public enum MessageType {
        PLAYER_DMG(new Color(188, 172, 2)),
        MONSTER_DMG(Color.white),
        PLAYER_INFO(new Color(2, 132, 188)),
        ;

        public final Color color;

        MessageType(Color color) {
            this.color = color;
        }

        boolean reverseSwerve() {
            return this.equals(PLAYER_INFO);
        }
    }
}
