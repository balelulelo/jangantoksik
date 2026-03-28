package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.engine.game.Positionable;

public class Dirt extends Tile implements PlayerOverHook {
    private boolean isPaved = false;

    public Dirt(Positionable position){
        super(position);
    }
}
