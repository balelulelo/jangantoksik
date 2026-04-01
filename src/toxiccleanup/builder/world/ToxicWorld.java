package toxiccleanup.builder.world;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.Tickable;
import toxiccleanup.builder.entities.tiles.Tile;
import toxiccleanup.builder.entities.tiles.ToxicField;
import toxiccleanup.builder.ui.RenderableGroup;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.engine.renderer.Dimensions;
import toxiccleanup.engine.renderer.Renderable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * a class representing the game world that manages a collection of tiles
 *
 */

public class ToxicWorld implements RenderableGroup, Tickable, World {
    /** list of all tiles in the world. */
    private List<Tile> tiles;

    /**
     * constructs an empty toxic world.
     */
    public ToxicWorld() {
        this.tiles = new ArrayList<>();
    }

    /**
     * retrieves all tiles at a specific pixel position
     *
     * @param position the pixel position to check for
     * @param dimensions the game dimensions for coordinate conversion
     * @return list of tiles at the specified position
     */

    public List<Tile> tilesAtPosition(Positionable position, Dimensions dimensions) {
        List<Tile> matchTiles = new ArrayList<>();
        // get the index grid target from our searched pixel
        int gridX = dimensions.pixelToTile(position.getX());
        int gridY = dimensions.pixelToTile(position.getY());
        // check for all tiles in the world
        for (Tile tile : tiles) {
            // change the position of tile pixel to grid index
            int tileX = dimensions.pixelToTile(tile.getX());
            int tileY = dimensions.pixelToTile(tile.getY());
            // if tile X/Y in the same cells grid, insert to list
            if (tileX == gridX && tileY == gridY) {
                matchTiles.add(tile);
            }
        }
        return matchTiles;
    }

    /**
     * checks if the world still contain any toxic fields
     *
     * @return true if there is at least one toxic field, otherwise return false
     */

    public boolean isToxic() {
        for (Tile tile : tiles) {
            if (tile instanceof ToxicField && ((ToxicField) tile).isToxic()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Tile> allTiles() {
        return new ArrayList<>(this.tiles);
    }

    @Override
    public void place(Tile tile) {
        if (tile != null) {
            tiles.add(tile);
        }
    }

    @Override
    public void tick(EngineState engine, GameState game) {
        List<Tile> currentTiles = new ArrayList<>(this.tiles);
        for (Tile tile : currentTiles) {
            tile.tick(engine, game);
        }
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> allRenderables = new ArrayList<>();

        for (Tile tile : tiles) {
            allRenderables.addAll(tile.render());
        }
        return allRenderables;
    }
}
