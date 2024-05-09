package nl.rrx.tile;

import nl.rrx.config.DependencyManager;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static nl.rrx.config.settings.ScreenSettings.TILE_SIZE;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_COL;
import static nl.rrx.config.settings.WorldSettings.MAX_WORLD_ROW;

public class TileManager {

    private final DependencyManager dm;

    private final Map<Integer, Tile> tiles = new HashMap<>();
    private final int[][] mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];

    public TileManager(DependencyManager dm) {
        this.dm = dm;
        loadTileImages();
        loadMap("/maps/map02.txt");
    }

    private void loadTileImages() {
        try {
            for (var type : TileType.values()) {
                var image = ImageIO.read(getClass().getResourceAsStream(type.imageUri));
                tiles.put(type.mapId, new Tile(image, type.isCollision));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            int screenX = worldX - dm.player.getWorldX() + dm.player.getScreenX();
            for (int worldRow = 0; worldRow < MAX_WORLD_ROW; worldRow++) {
                int worldY = worldRow * TILE_SIZE;
                int screenY = worldY - dm.player.getWorldY() + dm.player.getScreenY();

                if (isWithinScreenBoundary(worldX, worldY)) {
                    int tileNum = mapTileNum[worldCol][worldRow];
                    g2.drawImage(tiles.get(tileNum).image(), screenX, screenY, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }

    private boolean isWithinScreenBoundary(int worldX, int worldY) {
        return worldX > dm.player.getWorldX() - dm.player.getScreenX() - TILE_SIZE
            && worldX < dm.player.getWorldX() + dm.player.getScreenX() + TILE_SIZE
            && worldY > dm.player.getWorldY() - dm.player.getScreenY() - TILE_SIZE
            && worldY < dm.player.getWorldY() + dm.player.getScreenY() + TILE_SIZE;
    }

    public int getTileNum(int x, int y) {
        return mapTileNum[x][y];
    }

    public Tile getTile(int tileNum) {
        return tiles.get(tileNum);
    }
}
