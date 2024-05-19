package nl.rrx.object;

public enum GameObjectType {
    KEY("Key", "/images/objects/key.png"),
    DOOR("Door", "/images/objects/door.png"),
    CHEST("Chest", "/images/objects/chest.png");

    public final String id;
    public final String imageUri;


    GameObjectType(String id, String imageUri) {
        this.id = id;
        this.imageUri = imageUri;
    }
}
