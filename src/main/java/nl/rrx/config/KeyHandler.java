package nl.rrx.config;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.state.GameState;
import nl.rrx.ui.TitleScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static nl.rrx.config.DependencyManager.STATE_MGR;

public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (STATE_MGR.currentState()) {
            case PLAY -> handleKeysForPlayState(code);
            case PAUSE -> handleKeysForPauseState(code);
            case DIALOGUE -> handleKeysForDialogueState(code);
            case TITLE_SCREEN -> handleKeysForTitleScreen(code);
        }
    }

    private void handleKeysForPlayState(int code) {
        if (DebugSettings.ENABLED && code == KeyEvent.VK_Q) System.exit(0);

        if (code == KeyEvent.VK_P) {
            STATE_MGR.pressPause();
            return;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            return;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    private void handleKeysForPauseState(int code) {
        if (code == KeyEvent.VK_P) {
            STATE_MGR.pressPause();
        }
    }

    private void handleKeysForDialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            STATE_MGR.setState(GameState.PLAY);
        }
    }

    private void handleKeysForTitleScreen(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            TitleScreen.pressUp();
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            TitleScreen.pressDown();
        }
        if (code == KeyEvent.VK_ENTER) {
            TitleScreen.pressMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean noMovementKeysPressed() {
        return upPressed
                && downPressed
                && leftPressed
                && rightPressed;
    }
}
