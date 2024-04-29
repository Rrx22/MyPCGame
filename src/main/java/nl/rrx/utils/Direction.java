package nl.rrx.utils;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public int moveX(int speed) {
        return switch (this) {
            case RIGHT -> speed;
            case LEFT -> -speed;
            default -> 0;
        };
    }

    public int moveY(int speed) {
        return switch (this) {
            case DOWN -> speed;
            case UP -> -speed;
            default -> 0;
        };
    }
}
