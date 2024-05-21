package nl.rrx.object;

public enum GameObjectType {
    KEY("/images/objects/key.png", false),
    DOOR("/images/objects/door.png", true),
    CHEST("/images/objects/chest.png", true);

    public final String imageUri;
    public final boolean isCollision;

    GameObjectType(String imageUri, boolean isCollision) {
        this.imageUri = imageUri;
        this.isCollision = isCollision;
    }
}
