package nl.rrx.event;

import nl.rrx.sprite.Direction;

public record Event(EventType type, int col, int row, Direction requiredDirection) {}
