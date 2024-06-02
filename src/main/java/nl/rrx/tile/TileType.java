package nl.rrx.tile;

public enum TileType {
    EARTH   (0, false, "/images/tilesv2/earth.png"),
    FLOOR01 (1, false, "/images/tilesv2/floor01.png"),
    GRASS00 (2, false, "/images/tilesv2/grass00.png"),
    GRASS01 (3, false, "/images/tilesv2/grass01.png"),
    HUT     (4, false, "/images/tilesv2/hut.png"),
    ROAD00  (5, false, "/images/tilesv2/road00.png"),
    ROAD01  (6, false, "/images/tilesv2/road01.png"),
    ROAD02  (7, false, "/images/tilesv2/road02.png"),
    ROAD03  (8, false, "/images/tilesv2/road03.png"),
    ROAD04  (9, false, "/images/tilesv2/road04.png"),
    ROAD05  (10, false, "/images/tilesv2/road05.png"),
    ROAD06  (11, false, "/images/tilesv2/road06.png"),
    ROAD07  (12, false, "/images/tilesv2/road07.png"),
    ROAD08  (13, false, "/images/tilesv2/road08.png"),
    ROAD09  (14, false, "/images/tilesv2/road09.png"),
    ROAD10  (15, false, "/images/tilesv2/road10.png"),
    ROAD11  (16, false, "/images/tilesv2/road11.png"),
    ROAD12  (17, false, "/images/tilesv2/road12.png"),
    TABLE01 (18, false, "/images/tilesv2/table01.png"),
    TREE    (19, true,  "/images/tilesv2/tree.png"),
    WALL    (20, true,  "/images/tilesv2/wall.png"),
    WATER00 (21, true,  "/images/tilesv2/water00.png"),
    WATER01 (22, true,  "/images/tilesv2/water01.png"),
    WATER02 (23, true,  "/images/tilesv2/water02.png"),
    WATER03 (24, true,  "/images/tilesv2/water03.png"),
    WATER04 (25, true,  "/images/tilesv2/water04.png"),
    WATER05 (26, true,  "/images/tilesv2/water05.png"),
    WATER06 (27, true,  "/images/tilesv2/water06.png"),
    WATER07 (28, true,  "/images/tilesv2/water07.png"),
    WATER08 (29, true,  "/images/tilesv2/water08.png"),
    WATER09 (30, true,  "/images/tilesv2/water09.png"),
    WATER10 (31, true,  "/images/tilesv2/water10.png"),
    WATER11 (32, true,  "/images/tilesv2/water11.png"),
    WATER12 (33, true,  "/images/tilesv2/water12.png"),
    WATER13 (34, true,  "/images/tilesv2/water13.png");

    public final int mapId;
    public final boolean isCollision;
    public final String imageUri;

    TileType(int mapId, boolean isCollision, String imageUri) {
        this.mapId = mapId;
        this.isCollision = isCollision;
        this.imageUri = imageUri;
    }
}
