package toxiccleanup.builder.entities.tiles;

import toxiccleanup.engine.game.Positionable;

public class TileFactory {

    public static Tile fromSymbol(Positionable position, char symbol){
        // requirement before we can actually process it, based on javadoc
        if(position.getX() < 0 || position.getY() < 0){
            throw new IllegalArgumentException("Position cannot be negative");
        }

        if (symbol == 'd'){
            return new Dirt(position);
        } else if (symbol == 't'){
            return new ToxicField(position);
        } else if (symbol == 'g'){
            return new Grass(position);
        } else if (symbol == 'l'){
            return new Chasm(position, "left");
        } else if (symbol == 'L'){
            return new Chasm(position, "leftslope");
        } else if (symbol == 'r'){
            return new Chasm(position, "right");
        } else if (symbol == 'R') {
            return new Chasm(position, "rightslope");
        } else if (symbol == 'c') {
            return new Chasm(position);
        } else{
            throw new IllegalArgumentException("Unknown symbol: " + symbol);
        }
    }
}
