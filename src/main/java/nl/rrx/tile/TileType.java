package nl.rrx.tile;

public enum TileType {
    DESERT("/images/tiles/desert.png"),
    WALL("/images/tiles/wall.png"),
    WATER("/images/tiles/water.png");

    private String imageUri;

    TileType(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }
}
