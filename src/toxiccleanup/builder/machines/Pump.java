package toxiccleanup.builder.machines;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Positionable;

/**
 * A {@link Pump} is a machine that removes toxicity from a {toxiccleanup.builder.entities.tiles.ToxicField}
 * over time. Every 100 game ticks it calls {@link Adjustable#adjust(int)} on
 * its target with {@code 1}, reducing the field's toxicity by 1. The pump only operates when
 * the shared power system has at least 2 power units available - if power drops below 2, both
 * the animation and the pumping pause until power is restored.
 *
 * <p>The pump stops and is removed from the field once the field's toxicity reaches 0 (i.e.
 * when the field is fully cleaned up).
 *
 * <p>Costs {COST} power units to build. The pump cycles through a sprite animation every
 * 4 ticks while powered. Rendered using {@link SpriteGallery#pump}.
 *
 * <p><span style="color:#9B59B6;">Provided:</span> The class is provided without
 * {@code extends} or {@code implements} clauses, and with no fields or methods.
 *
 * @provided
 * @stage3
 */
public class Pump extends GameEntity implements Powered {
    public static final int COST = 5;
    private Adjustable pumpTarget;
    // 100 ticks for pump timer, and 4 ticks for the pump animation
    private int pumpTimer = 100;
    private int spriteAnimation = 4;
    private int frame = 1;

    public Pump(Positionable position, Adjustable pumpTarget) {
        super(position);
        this.pumpTarget = pumpTarget;
        this.setSprite(SpriteGallery.pump.getSprite("1"));
    }

    @Override
    public void tick(EngineState state, GameState game) {
        super.tick(state, game);
        // pump can only be used if power more than / is 2
        if (game.getMachines().getPower() >= getPowerRequirement()) {

            // ====== Animation ======
            spriteAnimation--;
            if (spriteAnimation <= 0) {
                frame = (frame % 8) + 1;
                // get the sprite of pump from 1 until 10
                setSprite(SpriteGallery.pump.getSprite(String.valueOf(frame)));
                // every 4 ticks (4 -> 3 -> 2 -> 1 -> 0 (start animating))
                spriteAnimation = 4;
            }

            pumpTimer--;
            // if pumpTimer reaches 0, reduces the toxic target, and resets the timer to 100 ticks
            if (pumpTimer <= 0) {
                pumpTarget.adjust(1);
                pumpTimer = 100;
            }
        }
    }

    @Override
    public int getPowerRequirement() {
        // the power required to use pump is 2
        return 2;
    }
}
