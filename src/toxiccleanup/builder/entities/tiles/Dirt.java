package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.Tickable;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.builder.machines.Machines;
import toxiccleanup.builder.machines.SolarPanel;
import toxiccleanup.builder.machines.Teleporter;
import toxiccleanup.builder.ui.RenderableGroup;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.game.HasTick;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.engine.renderer.Renderable;

import java.util.List;

/**
 * A tile of dirt that can be paved and used as the base for building machines
 */

public class Dirt extends Tile implements PlayerOverHook,
        Tickable, RenderableGroup, HasTick, Positionable, Renderable {
    /**
     * boolean whether the dirt tile is already paved or not.
     */
    private boolean isPaved = false;

    /**
     * constructs a new dirt tile at the given position
     *
     * @param position the pixel position of the dirt tile
     *
     */
    public Dirt(Positionable position) {
        super(position, SpriteGallery.dirt);
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            // if the art is not found (ex: wrong name), display error
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    /**
     * check if the tile is already paved or not.
     *
     * @return true if the dirt tile is paved, otherwise return false
     *
     */
    public boolean isPaved() {
        return isPaved;
    }

    /**
     * paves the dirt tile to allow machines on top of it
     */
    public void pave() {
        this.isPaved = true;
        // change the gallery to paved from dirt
        this.setArt(SpriteGallery.paved);
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            // if the sprite is missing, skip updateSprite
        }
    }

    /**
     * attempts to spawn a solar panel on this tile with the provided spawner
     *
     * @param spawner machine manager to spawn solar panel
     *
     */

    public void attemptSpawnSolarPanel(Machines spawner) {
        SolarPanel solarPanel = spawner.spawnSolarPanel(getPosition());
        if (solarPanel != null) {
            placeOn(solarPanel);
        }
    }

    /**
     * attempts to spawn a teleporter on this tile with the provided spawner
     *
     * @param spawner machine manager to spawn teleporter
     *
     */

    public void attemptSpawnTeleporter(Machines spawner) {
        Teleporter teleporter = spawner.spawnTeleporter(getPosition());

        if (teleporter != null) {
            this.placeOn(teleporter);
        }
    }

    @Override
    public void playerOver(EngineState state, GameState game) {
        super.playerOver(state, game);

        // === if not yet paved but user pressed F already:
        if (!this.isPaved() && state.getKeys().isDown('f')) {
            // calls pave method above and pave the dirt
            pave();
        }

        // === if the dirt is already paved, and there is no stacked entity on top of the dirt
        if (this.isPaved() && getStackedEntities().isEmpty()) {
            // if the user left-click on their mouse. places the solar panel
            if (state.getMouse().isLeftPressed()) {
                attemptSpawnSolarPanel(game.getMachines());

            }
            // if the user right-click on their mouse. place the teleporter
            if (state.getMouse().isRightPressed()) {
                attemptSpawnTeleporter(game.getMachines());
            }
        }
    }
}
