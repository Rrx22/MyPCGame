package nl.rrx.tile;

import nl.rrx.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static nl.rrx.config.ScreenSettings.MAX_WORLD_COL;
import static nl.rrx.config.ScreenSettings.MAX_WORLD_ROW;
import static nl.rrx.config.ScreenSettings.TILE_SIZE;

public class TileManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    private final List<Tile> tiles = new ArrayList<>();
    private final int[][] mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];
    private final GamePanel gamePanel;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadTileImages();
        loadMap("/maps/map02.txt");
    }

    public void loadTileImages() {

        try {
            for (var type : TileType.values()) {
                var tile = new Tile();
                tile.image = ImageIO.read(getClass().getResourceAsStream(type.getImageUri()));
                tiles.add(tile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int worldCol = 0; worldCol < MAX_WORLD_COL; worldCol++) {
            int screenX = getScreenX(worldCol);
            for (int worldRow = 0; worldRow < MAX_WORLD_ROW; worldRow++) {
                int screenY = getScreenY(worldRow);
                int tileNum = mapTileNum[worldCol][worldRow];
                g2.drawImage(tiles.get(tileNum).image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    private int getScreenX(int worldCol) {
        return (worldCol * TILE_SIZE) - gamePanel.player.getWorldX() + gamePanel.player.getScreenX();
    }
    private int getScreenY(int worldRow) {
        return (worldRow * TILE_SIZE) - gamePanel.player.getWorldY() + gamePanel.player.getScreenY();
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
}
