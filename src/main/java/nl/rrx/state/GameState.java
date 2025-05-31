package nl.rrx.state;

public enum GameState {
    PLAY,
    PAUSE,
    DIALOGUE,
    TITLE_SCREEN,
    CHARACTER_SCREEN,
    ;

    public boolean isPlayState() {
        return this.equals(PLAY);
    }
}
