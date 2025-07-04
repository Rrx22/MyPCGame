package nl.rrx.tile;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.util.PerformanceUtil;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_COL;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_ROW;
import static nl.rrx.util.ScreenUtil.getScreenX;
import static nl.rrx.util.ScreenUtil.getScreenY;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public class TileHandler {

    private static final String MAP_TXT_FILE = "/maps/island.txt";

    private final Map<Integer, Tile> tiles = new HashMap<>();
    private final int[][] mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];

    public TileHandler() {
        loadTileImages();
        loadMap();
    }

    private void loadTileImages() {
        for (var type : TileType.values()) {
            BufferedImage scaledImage = PerformanceUtil.getScaledImage(type.imageUri, TILE_SIZE, TILE_SIZE);
            tiles.put(type.mapId, new Tile(scaledImage, type.isCollision));
        }
    }

    public void loadMap() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(MAP_TXT_FILE)))) {
            for (int row = 0; row < MAX_WORLD_ROW; row++) {
                String[] parsedTileNumbers = reader.readLine().split(" ");
                for (int col = 0; col < MAX_WORLD_COL; col++) {
                    mapTileNum[col][row] = Integer.parseInt(parsedTileNumbers[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int worldCol = 0; worldCol < MAX_WORLD_COL; worldCol++) {
            int worldX = worldCol * TILE_SIZE;
            int screenX = getScreenX(worldX);
            for (int worldRow = 0; worldRow < MAX_WORLD_ROW; worldRow++) {
                int worldY = worldRow * TILE_SIZE;
                int screenY = getScreenY(worldY);

                if (isWithinScreenBoundary(PLAYER, worldX, worldY)) {
                    int tileNum = mapTileNum[worldCol][worldRow];
                    Tile tile = tiles.get(tileNum);
                    g2.drawImage(tile.image(), screenX, screenY, null);

                    doDebugDrawing(g2, tile, screenX, screenY, worldCol, worldRow);
                }
            }
        }
    }

    private void doDebugDrawing(Graphics2D g2, Tile tile, int screenX, int screenY, int worldCol, int worldRow) {
        if (DebugSettings.SHOW_COLLISION && tile.isCollision()) {
            var oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
            g2.setStroke(oldStroke);
        }
        if (DebugSettings.SHOW_COORDS) {
            g2.setFont(new Font("arial", Font.PLAIN, 10));
            g2.drawString(String.format("x:%02d", worldCol), screenX + 5, screenY + 10);
            g2.drawString(String.format("y:%02d", worldRow), screenX + 5, screenY + 20);
        }
    }

    public int getTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    public Tile getTile(int tileNum) {
        return tiles.get(tileNum);
    }
}
