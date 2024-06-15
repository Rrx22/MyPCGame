package nl.rrx.state;

public class StateManager {

    private GameState currentState;

    public GameState currentState() {
        return currentState;
    }

    public void setState(GameState gameState) {
        currentState = gameState;
    }

    public void pressPause() {
        currentState = (currentState == GameState.PLAY)
                ? GameState.PAUSE
                : GameState.PLAY;
    }
}
