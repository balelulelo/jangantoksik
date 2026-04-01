package toxiccleanup.builder.machines;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.Tickable;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.HasTick;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.engine.renderer.Renderable;

/**
 * A {@link SolarPanel} is a machine that passively generates power for the game's shared power
 * system. Once placed on a paved {toxiccleanup.builder.entities.tiles.Dirt} tile, it increments the
 * power in the {MachinesManager} by 1 every 120 game ticks (approximately every 2 seconds
 * at 60 ticks per second). Power is capped at the machine manager's maximum (14 by default).
 *
 * <p>Costs {COST} power units to build. Rendered using {@link SpriteGallery#solarPanel}.
 *
 * <p><span style="color:#9B59B6;">Provided:</span> The class is provided without
 * {@code extends} or {@code implements} clauses, and with no fields or methods.
 *
 * @provided
 * @stage3
 */
public class SolarPanel extends GameEntity {
    // timer is 120 frames (interval berween power generation)
    private int timer = 120;
    public static final int COST = 3;

    /**
     * constructs a solar panel at a specified position
     *
     * @param position the pixel position
     */
    public SolarPanel(Positionable position) {
        super(position);
        this.setSprite(SpriteGallery.solarPanel.getSprite("default"));
        this.timer = timer;
    }

    @Override
    public void tick(EngineState state, GameState game) {
        super.tick(state, game);
        // each frame, reduce the timer
        timer--;

        if (timer <= 0) {
            // every 120 ticks, adds 1 power to machine power
            game.getMachines().adjust(1);
            // after that, resets the timer afterwards
            timer = 120;
        }


    }
}
