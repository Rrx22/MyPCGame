package nl.rrx.tile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static nl.rrx.config.ScreenSettings.MAX_SCREEN_COL;
import static nl.rrx.config.ScreenSettings.MAX_SCREEN_ROW;
import static nl.rrx.config.ScreenSettings.TILE_SIZE;

public class TileManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    private final List<Tile> tiles = new ArrayList<>();
    private final int[][] parsedMapOfNumbers = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];

    public TileManager() {
        loadTileImages();
        loadMap("/maps/map01.txt");
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
        int x = 0;
        int y = 0;

        for (int col = 0; col < MAX_SCREEN_COL; col++) {
            for (int row = 0; row < MAX_SCREEN_ROW; row++) {
                int parsedTileNumber = parsedMapOfNumbers[col][row];
                g2.drawImage(tiles.get(parsedTileNumber).image, x, y, TILE_SIZE, TILE_SIZE, null);
                y += TILE_SIZE;
            }
            x += TILE_SIZE;
            y = 0;
        }
    }

    private void loadMap(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {
            for (int row = 0; row < MAX_SCREEN_ROW; row++) {
                String[] parsedTileNumbers = reader.readLine().split(" ");
                for (int col = 0; col < MAX_SCREEN_COL; col++) {
                    parsedMapOfNumbers[col][row] = Integer.parseInt(parsedTileNumbers[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
