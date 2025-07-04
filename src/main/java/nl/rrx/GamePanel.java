package nl.rrx;

import nl.rrx.common.SortedDrawable;
import nl.rrx.config.FpsHandler;
import nl.rrx.config.settings.DebugSettings;
import nl.rrx.state.GameState;
import nl.rrx.ui.FloatingBattleMessages;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.EVENT_MGR;
import static nl.rrx.config.DependencyManager.KEY_HANDLER;
import static nl.rrx.config.DependencyManager.MONSTER_MGR;
import static nl.rrx.config.DependencyManager.NPC_MGR;
import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.config.DependencyManager.TILE_HANDLER;
import static nl.rrx.config.DependencyManager.UI;
import static nl.rrx.config.settings.ScreenSettings.SCREEN_SIZE;

public class GamePanel extends JPanel implements Runnable {

    private transient Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(SCREEN_SIZE);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KEY_HANDLER);
        this.setFocusable(true);
    }

    public void setUpGame() {
        STATE_HANDLER.setStartState();
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
            }
        }
    }

    private void update() {
        if (STATE_HANDLER.currentState() == GameState.PLAY) {
            PLAYER.update();
            NPC_MGR.updateNPCs();
            MONSTER_MGR.updateMonsters();
            EVENT_MGR.checkEvent();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long drawStart = System.nanoTime();

        Graphics2D g2 = (Graphics2D) g;

        if (STATE_HANDLER.currentState() != GameState.TITLE_SCREEN) {
            TILE_HANDLER.draw(g2);
            EVENT_MGR.draw(g2);
            SortedDrawable.drawSpritesAndObjectsInOrder(g2);
            FloatingBattleMessages.draw(g2);
            if (DebugSettings.DRAW_DEBUG_STATS) UI.drawDebugStats(g2, drawStart); // this needs to happen right here
        }
        UI.draw(g2);

        g2.dispose();
    }
}
