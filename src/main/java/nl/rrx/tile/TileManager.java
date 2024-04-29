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

import static nl.rrx.config.ScreenSettings.MAX_SCREEN_COL;
import static nl.rrx.config.ScreenSettings.MAX_SCREEN_ROW;
import static nl.rrx.config.ScreenSettings.TILE_SIZE;

public class TileManager implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;

    private final GamePanel gp;
    private final List<Tile> tiles = new ArrayList<>();
    private final int[][] parsedMapOfNumbers = new int[MAX_SCREEN_COL][MAX_SCREEN_ROW];

    public TileManager(GamePanel gp) {
        this.gp = gp;
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

    public void loadMap(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)))) {
            int col = 0;
            int row = 0;

            while (col < MAX_SCREEN_COL && row < MAX_SCREEN_ROW) {
                String line = reader.readLine();
                String[] numbers = line.split(" ");
                while (col < MAX_SCREEN_COL) {
                    int num = Integer.parseInt(numbers[col]);
                    parsedMapOfNumbers[col][row] = num;
                    col++;
                }
                col = 0;
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < MAX_SCREEN_COL && row < MAX_SCREEN_ROW) {
            int parsedTileNumber = parsedMapOfNumbers[col][row];

            g2.drawImage(tiles.get(parsedTileNumber).image, x, y, TILE_SIZE, TILE_SIZE, null);
            col++;
            x += TILE_SIZE;

            if (col == MAX_SCREEN_COL) {
                col = 0;
                x = 0;
                row++;
                y += TILE_SIZE;
            }
        }
    }
}
