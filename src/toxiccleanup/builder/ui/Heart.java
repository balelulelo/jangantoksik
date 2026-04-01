package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Position;
import toxiccleanup.engine.game.Positionable;

/**
 * A HUD icon representing one unit of the player's HP
 *
 */
public class Heart extends GameEntity {
    /**
     * Constructs a Heart icon at the given position
     *
     * @param position the pixel position of the heart
     *
     */
    public Heart(Positionable position) {
        super(position);
        this.setSprite(SpriteGallery.heart.getSprite("default"));
    }
}
