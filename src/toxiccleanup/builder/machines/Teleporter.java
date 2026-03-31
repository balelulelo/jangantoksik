package toxiccleanup.builder.machines;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Positionable;

/**
 * A {@link Teleporter} is a machine that allows the player to instantly travel between teleporter
 * locations on the map. When the player stands on a teleporter and presses the use key ('e'),
 * they are moved to a randomly chosen other teleporter's position — provided the shared power
 * system has at least {COST} power units available. Power is NOT consumed on use;
 * it is only required to be present.
 *
 * <p>Costs {COST} power units to build. When powered, cycles through a sprite animation
 * every 12 ticks. If power drops below the requirement, the animation pauses. Rendered using
 * {@link SpriteGallery#teleporter}.
 *
 * <p><span style="color:#9B59B6;">Provided:</span> The class is provided without
 * {@code extends} or {@code implements} clauses, and with no fields or methods.
 *
 * @provided
 * @stage3
 */
public class Teleporter extends GameEntity implements PlayerOverHook, Powered {
    public static final int COST = 2;
    // cycles sprite animation every 12 ticks
    private int spriteAnimation = 12;
    private int frame = 1;

    public Teleporter(Positionable position) {
        super(position);
        this.setSprite(SpriteGallery.teleporter.getSprite("1"));
    }

    @Override
    public void tick(EngineState state, GameState game) {
        super.tick(state, game);

        if (game.getMachines().getPower() >= getPowerRequirement()) {
            spriteAnimation--;
            if (spriteAnimation <= 0) {
                frame = (frame % 7) + 1;
                // get the sprite of pump from 1 until 8
                setSprite(SpriteGallery.teleporter.getSprite(String.valueOf(frame)));
                // every 12 ticks (12 -> 11 -> 11 -> ... -> 0 (start animating))
                spriteAnimation = 12;
            }

        }

    }

    @Override
    public int getPowerRequirement() {
        return COST;
    }

    @Override
    public void playerOver(EngineState state, GameState game) {
        // if player has the required power (at least 2) and "E" button is pressed
        if ((game.getMachines().getPower() >= getPowerRequirement()) && state.getKeys().isDown('e')) {
            Positionable position = game.getMachines().getNextTeleporterPosition(getPosition());

            // move the player to another teleporter destination
            if (position != null) {
                game.getPlayer().setPosition(position);
            }
        }
    }
}
