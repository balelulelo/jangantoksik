package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Positionable;

/**
 * A HUD element representing one unit of the power bar
 *
 */
public class PowerBar extends GameEntity {

    /**
     * Constructs a PowerBar in the uncharged visual state at the given position.
     *
     * @param position the pixel position of the power bar
     */
    public PowerBar(Positionable position) {
        // constructor for an uncharged power bar
        // calls the constructor below with false as the default state (uncharged)
        this(position, false);
    }

    /**
     * Constructs a PowerBar in either the charged or uncharged visual state at the given position.
     *
     * @param position the pixel position of the power bar
     * @param powerCharged a flag indicating if the newly constructed PowerBar should be set to it's charged state.
     *
     */
    public PowerBar(Positionable position, boolean powerCharged) {
        // constructor for a bar which status can be determined
        super(position);
        // if powerCharged is true, sprite name is charged, otherwise it's uncharged
        String sprite = powerCharged ? "chargedbar" : "bar";
        this.setSprite(SpriteGallery.power.getSprite(sprite));
    }

}
