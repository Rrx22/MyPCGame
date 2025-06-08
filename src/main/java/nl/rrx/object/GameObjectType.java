package nl.rrx.object;

public enum GameObjectType {
    KEY("/images/objects/key.png", false, true, "Key", "Unlocks a door."),
    DOOR("/images/objects/door.png", true, false),
    CHEST("/images/objects/chest.png", true, false),
    BOOTS("/images/objects/boots.png", false, true, "Boots", "To wear on your feet."),
    HEART("/images/objects/heart_full.png", false, false),
    ;

    public final String imageUri;
    public final boolean isCollision;
    public final boolean canStash;
    public final String title;
    public final String description;

    GameObjectType(String imageUri, boolean isCollision, boolean canStash, String title, String description) {
        this.imageUri = imageUri;
        this.isCollision = isCollision;
        this.canStash = canStash;
        this.title = title;
        this.description = description;
    }
    GameObjectType(String imageUri, boolean isCollision, boolean canStash) {
        if (canStash) {
            throw new RuntimeException("Stashable object types required a TITLE and DESCRIPTION");
        }
        this(imageUri, isCollision, false, "", "");
    }
}
