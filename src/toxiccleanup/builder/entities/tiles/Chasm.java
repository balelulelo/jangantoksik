package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.machines.Adjustable;

import java.util.List;

public class Chasm extends Tile implements PlayerOverHook {
    private boolean fallable;

    public Chasm(Positionable position) {
        super(position, SpriteGallery.chasm);
        this.fallable = true;
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    public Chasm(Positionable position, String facing) {
        super(position, SpriteGallery.chasm);
        // pre condition:
        if (!List.of("left", "leftslope", "right", "rightslope").contains(facing)) {
            throw new IllegalArgumentException("Invalid facing sprite");
        }
        this.fallable = false;
        try {
            updateSprite(facing);
        } catch (ArtNotFoundException exception) {
            System.out.println("Art not found: " + exception.getMessage());
        }
    }

    @Override
    public void playerOver(EngineState engine, GameState game) {
//        super.playerOver(engine, game);
//        if(fallable){
//            game.getPlayer().adjust(1);
//        }
// I'll turn this off for now (so I don't keep dying)
    }

}
