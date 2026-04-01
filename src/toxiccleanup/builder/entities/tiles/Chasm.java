package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.machines.Adjustable;

import java.util.List;

/**
 * A chasm tile that represent the huge hole in the game world.
 * Causes player the fall and take damage, instantly killing them
 */
public class Chasm extends Tile implements PlayerOverHook {
    /** boolean whether player could fall into the chasm tile or not. */
    private boolean fallable;
    /**
     * constructs a chasm tile at the specified position
     * @param position the pixel position where chasm will be placed
     *
     */

    public Chasm(Positionable position) {
        super(position, SpriteGallery.chasm);
        this.fallable = true;
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    /**
     * constructs a chasm tile with specific orientation.
     *
     * @param position the pixel position where the chasm will be placed.
     * @param facing the direction of the chasm edge (right, left, etc).
     *
     */
    public Chasm(Positionable position, String facing) {
        super(position, SpriteGallery.chasm);
        // pre condition:
        if (!List.of("left", "leftslope", "right", "rightslope").contains(facing)) {
            throw new IllegalArgumentException("Invalid facing sprite");
        }
        this.fallable = false;
        try {
            updateSprite(facing);
        } catch (ArtNotFoundException exception) {
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    @Override
    public void playerOver(EngineState engine, GameState game) {
        super.playerOver(engine, game);
        if (fallable) {
            game.getPlayer().adjust(1);
        }

    }

}
