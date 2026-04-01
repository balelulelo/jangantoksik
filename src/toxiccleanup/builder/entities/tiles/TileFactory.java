package toxiccleanup.builder.entities.tiles;

import toxiccleanup.engine.game.Positionable;

/**
 * A factory class used to create a tile instances based on the map symbols
 *
 */

public class TileFactory {

    /**
     * creates a tile object that corresponds on the provided character symbol
     *
     * @param position pixel position for the new tile
     * @param symbol the character symbol from the map file
     * @return a tile instances (example: Dirt, ToxicField, Grass, etc)
     *
     */

    public static Tile fromSymbol(Positionable position, char symbol) {
        // requirement before we can actually process it, based on javadoc
        if (position.getX() < 0 || position.getY() < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }

        if (symbol == 'd') {
            return new Dirt(position);
        } else if (symbol == 't') {
            return new ToxicField(position);
        } else if (symbol == 'g') {
            return new Grass(position);
        } else if (symbol == 'l') {
            return new Chasm(position, "left");
        } else if (symbol == 'L') {
            return new Chasm(position, "leftslope");
        } else if (symbol == 'r') {
            return new Chasm(position, "right");
        } else if (symbol == 'R') {
            return new Chasm(position, "rightslope");
        } else if (symbol == 'c') {
            return new Chasm(position);
        } else {
            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }
    }
}
