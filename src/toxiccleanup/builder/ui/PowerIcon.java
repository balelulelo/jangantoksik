package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Positionable;

/**
 * a HUD icon displayed in the top-left corner of the screen to label the power bar
 */
public class PowerIcon extends GameEntity {
    /**
     * Constructs a new PowerIcon instance.
     *
     * @param position the pixel position of the power icon
     *
     */
    public PowerIcon(Positionable position) {
        super(position);
        this.setSprite(SpriteGallery.power.getSprite("icon"));
    }
}
