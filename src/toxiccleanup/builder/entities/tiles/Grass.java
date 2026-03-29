package toxiccleanup.builder.entities.tiles;

import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.engine.art.ArtNotFoundException;


public class Grass extends Tile{

    public Grass(Positionable position){
        super(position, SpriteGallery.grass);
        try{
            updateSprite("Grass");
        }catch (ArtNotFoundException exception){
            System.out.println("Art not found: " + exception.getMessage());
        }
    }
}
