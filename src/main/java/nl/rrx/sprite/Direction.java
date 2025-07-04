package nl.rrx.sprite;

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

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
