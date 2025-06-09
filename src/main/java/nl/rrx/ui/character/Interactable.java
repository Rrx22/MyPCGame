package nl.rrx.ui.character;

import nl.rrx.sprite.Direction;

interface Interactable {
    boolean moveCursor(Direction direction);
    void doAction();
}
