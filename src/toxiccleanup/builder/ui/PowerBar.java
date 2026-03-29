package toxiccleanup.builder.ui;

import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.game.Positionable;

public class PowerBar extends GameEntity {

    public PowerBar(Positionable position){
        // constructor for an uncharged power bar
        // calls the constructor below with false as the default state (uncharged)
        this(position, false);
    }
    public PowerBar(Positionable position, boolean powerCharged){
        // constructor for a bar which status can be determined
        super(position);
        // if powerCharged is true, sprite name is charged, otherwise it's uncharged
        String sprite = powerCharged ? "chargedbar" : "bar";
        this.setSprite(SpriteGallery.power.getSprite(sprite));
    }

}
