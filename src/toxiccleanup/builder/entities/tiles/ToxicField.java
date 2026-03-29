package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.machines.Adjustable;
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
        this.toxicity = Math.max(0, this.toxicity - amount);
        updateVisuals();
    }

    public boolean isToxic() {
        return this.toxicity > 0;
    }

    private void updateVisuals() {
        try {
            updateSprite("default");
        } catch (ArtNotFoundException exception) {
        }
    }
}