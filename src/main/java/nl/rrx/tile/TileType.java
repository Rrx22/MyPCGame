package nl.rrx.tile;

public enum TileType {
    GRASS (0, false, "/images/tiles/grass.png"),
    WALL  (1, true, "/images/tiles/wall.png"),
    WATER (2, true, "/images/tiles/water.png"),
    DIRT  (3, false, "/images/tiles/dirt.png"),
    DESERT(4, false, "/images/tiles/desert.png"),
    TREE  (5, true, "/images/tiles/tree.png");

    public final int mapId;
    public final boolean isCollision;
    public final String imageUri;

    TileType(int mapId, boolean isCollision, String imageUri) {
        this.mapId = mapId;
        this.isCollision = isCollision;
        this.imageUri = imageUri;
    }
}
