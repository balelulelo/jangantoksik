package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Position;
import toxiccleanup.engine.game.Positionable;

public class Heart extends GameEntity {
    public Heart(Positionable position){
        super(position);
        this.setSprite(SpriteGallery.heart.getSprite("default"));
    }
}
