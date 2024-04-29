package nl.rrx;

import nl.rrx.entity.Player;
import nl.rrx.utils.FpsUtil;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    private static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    private static final int SCALE = 3;

    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    private static final int MAX_SCREEN_COL = 16;
    private static final int MAX_SCREEN_ROW = 12;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    private static final int FPS = 60;



    /**
     * Dependencies
     */
    private KeyHandler keyH = new KeyHandler();
    private Thread gameThread;
    private final Player player = new Player(keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
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
        var fpsUtil = new FpsUtil(FPS);
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
        player.draw(g2, TILE_SIZE);
        g2.dispose();
    }

}
