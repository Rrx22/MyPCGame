package nl.rrx.tile;

import nl.rrx.config.settings.DebugSettings;
import nl.rrx.sprite.Player;
import nl.rrx.util.PerformanceUtil;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_COL;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_ROW;
import static nl.rrx.util.ScreenUtil.isWithinScreenBoundary;

public class TileManager {

    private final Player player;

    private final Map<Integer, Tile> tiles = new HashMap<>();
    private final int[][] mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];

    public TileManager(Player player) {
        this.player = player;
        loadTileImages();
        loadMap("/maps/island.txt");
    }

    private void loadTileImages() {
        for (var type : TileType.values()) {
            BufferedImage scaledImage = PerformanceUtil.getScaledImage(type.imageUri, TILE_SIZE, TILE_SIZE);
            tiles.put(type.mapId, new Tile(scaledImage, type.isCollision));
        }
    }

    private void loadMap(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {
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
            int screenX = worldX - player.getWorldX() + player.getScreenX();
            for (int worldRow = 0; worldRow < MAX_WORLD_ROW; worldRow++) {
                int worldY = worldRow * TILE_SIZE;
                int screenY = worldY - player.getWorldY() + player.getScreenY();

                if (isWithinScreenBoundary(player, worldX, worldY)) {
                    int tileNum = mapTileNum[worldCol][worldRow];
                    Tile tile = tiles.get(tileNum);
                    g2.drawImage(tile.image(), screenX, screenY, null);

                    if (DebugSettings.SHOW_COLLISION && tile.isCollision()) {
                        var oldStroke = g2.getStroke();
                        g2.setStroke(new BasicStroke(2));
                        g2.drawRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
                        g2.setStroke(oldStroke);
                    }
                }
            }
        }
    }

    public int getTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    public Tile getTile(int tileNum) {
        return tiles.get(tileNum);
    }
}
