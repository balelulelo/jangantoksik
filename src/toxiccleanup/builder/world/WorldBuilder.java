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

        for(int rows = 0; rows < lines.length; rows++){
            String line = new lines[rows];
            for(int cols = 0; cols < line.length(); cols++){
                char symbol = line.charAt(cols);

                Position position = new Position(cols*16, rows*16);

                try{
                    tiles.add(TileFactory.fromSymbol(position, symbol));
                } catch(IllegalArgumentException exception){
                    throw new WorldLoadException("Invalid map symbol: " + symbol);
                }
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
        return new ToxicWorld(tiles);
    }

}

