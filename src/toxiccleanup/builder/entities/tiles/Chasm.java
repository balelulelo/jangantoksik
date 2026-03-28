package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Positionable;

import java.util.List;

public class Chasm extends Tile implements PlayerOverHook {
    private boolean fallable;
    private String facing;

    public Chasm(Positionable position){
        super(position);
        this.fallable = true;
    }
    public Chasm(Positionable position, String facing){
        super(position);
        // pre condition:
        if(!List.of("left", "leftslope", "right", "rightslope").contains(facing)){
            throw new IllegalArgumentException("Invalid facing sprite");
        }
        this.fallable = false;
    }
    @Override
    public void playerOver(EngineState engine, GameState game){
        super.playerOver(engine, game);
        if(fallable){

        }
    }

}
