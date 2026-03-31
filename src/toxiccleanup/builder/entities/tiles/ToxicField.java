package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.machines.Adjustable;
import toxiccleanup.builder.machines.Pump;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.engine.art.ArtNotFoundException;

public class ToxicField extends Tile implements Adjustable {
    private int toxicity = 3;

    public ToxicField(Positionable position) {
        super(position, SpriteGallery.toxicField);
        updateVisuals();
    }

    @Override
    public void adjust(int amount) {
        // reduces the toxicity
        this.toxicity = Math.max(0, this.toxicity - amount);
        // the toxicity amount handling
        updateVisuals();
        // delete the pump if the toxic field is already clean
        if(!isToxic()){
            // for every entity above this tile
            for(GameEntity entity : getStackedEntities()){
                entity.markForRemoval();
            }
        }
    }

    public boolean isToxic() {
        return this.toxicity > 0;
    }

    @Override
    public void playerOver(EngineState state, GameState game) {
        super.playerOver(state, game);
        // if still toxic, tile is empty, and the player left-clicks their mouse.
        if(isToxic() && getStackedEntities().isEmpty() && state.getMouse().isLeftPressed()){
            Pump pump = game.getMachines().spawnPump(getPosition(), this);
            if (pump != null){
                placeOn(pump);
            }
        }
    }

    // private method to update toxic field sprite image
    // will use this since there are different sprites for toxic field
    private void updateVisuals() {
        try {
           if (toxicity == 3){
               updateSprite("default");
           } else if (toxicity == 2){
               updateSprite("cleanupstart");
           } else if (toxicity == 1){
               updateSprite("cleanupmid");
           } else{
               updateSprite("cleanupdone");
           }
        } catch (ArtNotFoundException exception) {
        }
    }


}