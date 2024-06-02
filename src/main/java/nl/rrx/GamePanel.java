package nl.rrx;

import nl.rrx.config.DependencyManager;
import nl.rrx.config.FpsHandler;
import nl.rrx.config.settings.DebugSettings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import static nl.rrx.config.settings.ScreenSettings.SCREEN_SIZE;

public class GamePanel extends JPanel implements Runnable {

    private final transient DependencyManager dm;
    private transient Thread gameThread;

    public GamePanel(DependencyManager dm) {
        this.dm = dm;
        this.setPreferredSize(SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(dm.keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame() {
        dm.soundManager.playMusic();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        var fpsHandler = new FpsHandler();
        while (gameThread != null) {
            if (fpsHandler.canLoadNextFrame()) {
                update();
                repaint();
                if (dm.player.isGameOver()) {
                    gameThread = null;
                }
            }
        }
    }

    private void update() {
        dm.player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long drawStart = System.nanoTime();

        Graphics2D g2 = (Graphics2D) g;
        dm.tileManager.draw(g2);
        dm.objectManager.draw(g2);
        dm.player.draw(g2);
        dm.ui.draw(g2);
        if (DebugSettings.DRAW_DEBUG_STATS) dm.ui.drawDebugStats(g2, drawStart);

        g2.dispose();
    }
}
