package toxiccleanup.builder.world;

import toxiccleanup.builder.entities.tiles.Tile;
import toxiccleanup.builder.entities.tiles.TileFactory;
import toxiccleanup.engine.game.Position;
import toxiccleanup.engine.renderer.Dimensions;
import toxiccleanup.engine.util.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {

    public static List<Tile> fromString(Dimensions dimensions, String text) throws WorldLoadException{
        List<Tile> tiles = new ArrayList<>();
        String[] lines = text.split("\n");

        int n = dimensions.windowSize() / dimensions.tileSize();

        // validate length of line (n)
        if(lines.length != n){
            throw new WorldLoadException("Invalid number of lines. Expected " + n);
        }

        for(int rows = 0; rows < n; rows++){
            String line = lines[rows];

            if (lines.length == n) {
                for (int cols = 0; cols < line.length(); cols++) {
                    char symbol = line.charAt(cols);

                    int pixelX = dimensions.tileToPixel(cols);
                    int pixelY = dimensions.tileToPixel(rows);
                    Position position = new Position(pixelX, pixelY);

                    try {
                        tiles.add(TileFactory.fromSymbol(position, symbol));
                    } catch (IllegalArgumentException exception) {
                        throw new WorldLoadException("Invalid map symbol: " + symbol);
                    }
                }
            } else {
                throw new WorldLoadException("Invalid line length at row " + rows);
            }
        }
        return tiles;
    }

    public static ToxicWorld fromFile(Dimensions dimensions, String filepath) throws IOException, WorldLoadException {
        FileManager fileManager = new FileManager();
        String content = fileManager.readFile(filepath);
        return fromTiles(fromString(dimensions, content));
    }

    public static ToxicWorld fromTiles(List<Tile> tiles){
        ToxicWorld toxicWorld = new ToxicWorld();

        for(int i = tiles.size() - 1; i >= 0; i--){
            toxicWorld.place(tiles.get(i));
        }
        return toxicWorld;
    }

}

