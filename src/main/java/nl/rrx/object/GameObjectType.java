package nl.rrx.object;

public enum GameObjectType {
    KEY("/images/objects/key.png", false, true),
    DOOR("/images/objects/door.png", true, false),
    CHEST("/images/objects/chest.png", true, false),
    BOOTS("/images/objects/boots.png", false, true),
    HEART("/images/objects/heart_full.png", false, false),
    ;

    public final String imageUri;
    public final boolean isCollision;
    public final boolean canStash;

    GameObjectType(String imageUri, boolean isCollision, boolean canStash) {
        this.imageUri = imageUri;
        this.isCollision = isCollision;
        this.canStash = canStash;
    }
}
