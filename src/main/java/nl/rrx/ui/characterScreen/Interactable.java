package nl.rrx.ui.characterScreen;

import nl.rrx.sprite.Direction;

interface Interactable {
    boolean moveCursor(Direction direction);
    void doAction();
}
