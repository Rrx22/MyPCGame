package nl.rrx.object.world;

public enum PlacedObjectFactory {
    DOOR("door.png", true),
    CHEST("chest.png", true),
    HEART("heart_full.png", false),
    ;

    public final String imageUri;
    public final boolean isCollision;
    public int offsetX = 0;
    public int offsetY = 0;

    PlacedObjectFactory(String imageUri, boolean isCollision) {
        this.imageUri = "/images/objects/" + imageUri;
        this.isCollision = isCollision;
    }

    /**
     * @param offsetX will widen the object (including the collision area)
     * @param offsetY will make the object taller (NOT including the collision area)
     */

    public PlacedObjectFactory withOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    public PlacedObject create(int worldX, int worldY) {
        return switch (this) {
            case DOOR -> new OBJ_Door(imageUri, isCollision, worldX, worldY, offsetX, offsetY);
            case CHEST -> null; // todo implement
            case HEART -> null; // todo implement
        };
    }
}
