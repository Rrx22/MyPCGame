package nl.rrx.tile;

public enum TileType {
    GRASS("/images/tiles/grass.png"),
    WALL("/images/tiles/wall.png"),
    WATER("/images/tiles/water.png"),
    DIRT("/images/tiles/dirt.png"),
    DESERT("/images/tiles/desert.png"),
    TREE("/images/tiles/tree.png");

    private final String imageUri;

    TileType(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }
}
