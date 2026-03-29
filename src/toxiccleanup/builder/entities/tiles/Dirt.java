package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;

public class Dirt extends Tile implements PlayerOverHook {
    private boolean isPaved = false;

    public Dirt(Positionable position){
        super(position, SpriteGallery.dirt);
        try{
            updateSprite("Dirt");
        } catch (ArtNotFoundException exception){
            System.out.println("Art not found: " + exception.getMessage());
        }
    }
    public boolean isPaved(){
        return isPaved;
    }
}
