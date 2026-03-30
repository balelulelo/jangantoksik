package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.builder.machines.Machines;
import toxiccleanup.builder.machines.SolarPanel;
import toxiccleanup.builder.machines.Teleporter;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;

import java.util.List;

public class Dirt extends Tile implements PlayerOverHook {
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

    public void attemptSpawnSolarPanel(Machines spawner){

    }

    public void attemptSpawnTeleporter(Machines spawner) {
        Teleporter teleporter = spawner.spawnTeleporter(getPosition());

        if (teleporter != null){
            this.placeOn(teleporter);
        }
    }

    @Override
    public void playerOver(EngineState state, GameState game) {
        super.playerOver(state, game);

        // === if not yet paved but user pressed F already:
        if (!this.isPaved() && state.getKeys().isDown('f')){
            // calls pave method above and pave t   he dirt
            pave();
        }

        // === if the user click on F button on keyboard (on a paved dirt)
        if (this.isPaved() && state.getStackedEntities().isEmpty()) {

            // if the user left-click on their mouse. places the solar panel
            if (state.getMouse().isLeftPressed()) {
                SolarPanel solarPanel = game.getMachines().spawnSolarPanel(getPosition());
                if (solarPanel != null) {
                    placeOn(solarPanel);
                }
            }
            // if the user right-click on their mouse. place the teleporter
            if (state.getMouse().isRightPressed()){
                attemptSpawnTeleporter(game.getMachines());
            }
        }
    }
}
