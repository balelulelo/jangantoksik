package toxiccleanup.builder.entities.tiles;

import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.engine.art.ArtNotFoundException;

/**
 * a grass tile that represents a non-toxic ground in the world
 */

public class Grass extends Tile {
    /**
     * constructs a new grass tile at the specified position
     *
     * @param position pixel position where grass will be placed
     */

    public Grass(Positionable position) {
        super(position, SpriteGallery.grass);
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            // if sprite is missing, display an error
            System.out.println("Art not found: " + exception.getMessage());
        }
    }
}
