package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Positionable;

public class PowerIcon extends GameEntity {
    public PowerIcon(Positionable position){
        super(position);
        this.setSprite(SpriteGallery.power.getSprite("icon"));
    }
}
