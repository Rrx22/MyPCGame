package nl.rrx.state;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.ui.character.UI_Character;

public class StateHandler {

    private GameState currentState;

    public GameState currentState() {
        return currentState;
    }

    public void setState(GameState gameState) {
        handleSwitchingCharacterScreen(gameState);
        currentState = gameState;
    }

    public void setStartState() {
        setState(DebugSettings.SKIP_TITLE_SCREEN
                ? GameState.PLAY
                : GameState.TITLE_SCREEN);
    }

    public void pressPause() {
        setState(currentState.isPlayState()
                ? GameState.PAUSE
                : GameState.PLAY);
    }

    public void toggleCharacterScreen() {
        if (currentState.isPlayState()) {
            setState(GameState.CHARACTER_SCREEN);
        } else {
            setState(currentState = GameState.PLAY);
        }
    }

    private void handleSwitchingCharacterScreen(GameState gameState) {
        if (gameState == GameState.CHARACTER_SCREEN) {
            UI_Character.init();
        }
        if (currentState == GameState.CHARACTER_SCREEN) {
            UI_Character.clear();
        }
    }
}
