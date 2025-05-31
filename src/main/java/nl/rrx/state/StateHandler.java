package nl.rrx.state;

import nl.rrx.config.settings.DebugSettings;

public class StateHandler {

    private GameState currentState;

    public GameState currentState() {
        return currentState;
    }

    public void setState(GameState gameState) {
        currentState = gameState;
    }

    public void setStartState() {
        currentState = DebugSettings.SKIP_TITLE_SCREEN
                ? GameState.PLAY
                : GameState.TITLE_SCREEN;
    }

    public void pressPause() {
        currentState = currentState.isPlayState()
                ? GameState.PAUSE
                : GameState.PLAY;
    }

    public void toggleCharacterScreen() {
        currentState = currentState.isPlayState()
                ? GameState.CHARACTER_SCREEN
                : GameState.PLAY;
    }
}
