package nl.rrx;

import nl.rrx.config.KeyHandler;
import nl.rrx.entity.Player;
import nl.rrx.tile.TileManager;
import nl.rrx.util.FpsUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import static nl.rrx.config.ScreenSettings.SCREEN_SIZE;

public class GamePanel extends JPanel implements Runnable {
    private transient Thread gameThread;
    private final KeyHandler keyH = new KeyHandler();
    private final TileManager tileM = new TileManager();
    private final Player player = new Player(keyH);

    public GamePanel() {
        this.setPreferredSize(SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        var fpsUtil = new FpsUtil();
        while (gameThread != null) {
            if (fpsUtil.canLoadNextFrame()) {
                update();
                repaint();
            }
        }
    }

    private void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
