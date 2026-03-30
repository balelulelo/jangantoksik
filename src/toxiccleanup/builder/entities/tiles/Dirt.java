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

public class Dirt extends Tile implements PlayerOverHook, Tickable, RenderableGroup, HasTick, Positionable, Renderable {
    private boolean isPaved = false;

    public Dirt(Positionable position) {
        super(position, SpriteGallery.dirt);
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    public boolean isPaved() {
        return isPaved;
    }

    public void pave() {
        this.isPaved = true;
        // change the gallery to paved from dirt
        this.setArt(SpriteGallery.paved);
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
        }
    }

    public void attemptSpawnSolarPanel(Machines spawner) {
        SolarPanel solarPanel = spawner.spawnSolarPanel(getPosition());
        if (solarPanel != null) {
            placeOn(solarPanel);
        }
    }

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
        playerOver(state, game);
    }
}
