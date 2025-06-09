package nl.rrx.config;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.sprite.Direction;
import nl.rrx.state.GameState;
import nl.rrx.ui.TitleScreen;
import nl.rrx.ui.characterScreen.CharacterScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.config.DependencyManager.TILE_HANDLER;

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

        switch (STATE_HANDLER.currentState()) {
            case PLAY -> handleKeysForPlayState(code);
            case PAUSE -> handleKeysForPauseState(code);
            case DIALOGUE -> handleKeysForDialogueState(code);
            case TITLE_SCREEN -> handleKeysForTitleScreen(code);
            case CHARACTER_SCREEN -> handleKeysForCharacterState(code);
        }
    }

    private void handleKeysForPlayState(int code) {
        if (DebugSettings.ENABLED && code == KeyEvent.VK_Q) System.exit(0);
        if (DebugSettings.ENABLED && code == KeyEvent.VK_R) TILE_HANDLER.loadMap();
        if (code == KeyEvent.VK_P) {
            STATE_HANDLER.pressPause();
            return;
        }
        if (code == KeyEvent.VK_C) {
            STATE_HANDLER.toggleCharacterScreen();
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
        if (code == KeyEvent.VK_P || code == KeyEvent.VK_Q) {
            STATE_HANDLER.pressPause();
        }
    }

    private void handleKeysForCharacterState(int code) {
        if (code == KeyEvent.VK_Q) {
            STATE_HANDLER.toggleCharacterScreen();
        }
        if (code == KeyEvent.VK_H) {
            CharacterScreen.toggleHelp();
        }
        if (code == KeyEvent.VK_C) {
            CharacterScreen.changeFocus();
        }
        if (code == KeyEvent.VK_ENTER) {
            CharacterScreen.doAction();
        }
        // stash
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            CharacterScreen.moveCursor(Direction.UP);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            CharacterScreen.moveCursor(Direction.DOWN);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            CharacterScreen.moveCursor(Direction.LEFT);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            CharacterScreen.moveCursor(Direction.RIGHT);
        }
    }

    private void handleKeysForDialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            STATE_HANDLER.setState(GameState.PLAY);
        } else if (code == KeyEvent.VK_C) {
            STATE_HANDLER.setState(GameState.CHARACTER_SCREEN);
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
        return !upPressed
                && !downPressed
                && !leftPressed
                && !rightPressed;
    }
}
