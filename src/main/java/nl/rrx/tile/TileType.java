package nl.rrx.tile;

public enum TileType {
    EARTH   (0, false, "earth.png"),
    FLOOR01 (1, false, "floor01.png"),
    GRASS00 (2, false, "grass00.png"),
    GRASS01 (3, false, "grass01.png"),
    HUT     (4, false, "hut.png"),
    ROAD00  (5, false, "road00.png"),
    ROAD01  (6, false, "road01.png"),
    ROAD02  (7, false, "road02.png"),
    ROAD03  (8, false, "road03.png"),
    ROAD04  (9, false, "road04.png"),
    ROAD05  (10, false, "road05.png"),
    ROAD06  (11, false, "road06.png"),
    ROAD07  (12, false, "road07.png"),
    ROAD08  (13, false, "road08.png"),
    ROAD09  (14, false, "road09.png"),
    ROAD10  (15, false, "road10.png"),
    ROAD11  (16, false, "road11.png"),
    ROAD12  (17, false, "road12.png"),
    TABLE01 (18, false, "table01.png"),
    TREE    (19, true,  "tree.png"),
    WALL    (20, true,  "wall.png"),
    WATER00 (21, true,  "water00.png"),
    WATER01 (22, true,  "water01.png"),
    WATER02 (23, true,  "water02.png"),
    WATER03 (24, true,  "water03.png"),
    WATER04 (25, true,  "water04.png"),
    WATER05 (26, true,  "water05.png"),
    WATER06 (27, true,  "water06.png"),
    WATER07 (28, true,  "water07.png"),
    WATER08 (29, true,  "water08.png"),
    WATER09 (30, true,  "water09.png"),
    WATER10 (31, true,  "water10.png"),
    WATER11 (32, true,  "water11.png"),
    WATER12 (33, true,  "water12.png"),
    WATER13 (34, true,  "water13.png"),
    HEALING_POOL (35, true,  "healingpool.png");

    public final int mapId;
    public final boolean isCollision;
    public final String imageUri;

    TileType(int mapId, boolean isCollision, String imageUri) {
        this.mapId = mapId;
        this.isCollision = isCollision;
        this.imageUri = "/images/tilesv2/" + imageUri;
    }
}
