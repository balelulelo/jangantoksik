package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.GameState;
import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.builder.ui.RenderableGroup;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.sprites.SpriteGroup;
import toxiccleanup.engine.game.HasTick;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.engine.renderer.Renderable;
import toxiccleanup.builder.SpriteGallery;
import toxiccleanup.engine.art.ArtNotFoundException;
import toxiccleanup.engine.art.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract base class for all ground tiles in the game world. Each tile occupies one cell
 * in the grid and is responsible for:
 *
 * <ul>
 *   <li>Displaying itself using a {@link SpriteGroup} set at construction (or changed later
 *       via {@link #setArt}).</li>
 *   <li>Maintaining a stack of {@link GameEntity}s that sit on top of it (e.g. a
 *       {@link toxiccleanup.builder.machines.SolarPanel} placed on a paved {@link Dirt} tile).</li>
 *   <li>Ticking itself and all stacked entities each frame, and removing any that are marked
 *       for removal.</li>
 *   <li>Implementing {@link PlayerOverHook#playerOver} to react when the player stands on this
 *       cell, and forwarding the event to any stacked entities that also implement the hook.</li>
 *   <li>Collecting all {@link Renderable}s for itself and its stack via {@link #render()}.</li>
 * </ul>
 *
 * <p>Concrete tile types ({@link Dirt}, {@link Grass}, {@link Chasm}, {@link ToxicField})
 * extend this class and implement their specific mechanics.
 *
 * <p><span style="color:#9B59B6;">Provided:</span> Starter code only; this class initially has no extends/implements, fields, or method bodies.
 *
 * <p><span style="color:#2E75B2;">Stage 1:</span> Extends {@link GameEntity} and implements
 * {@link PlayerOverHook}, {@link RenderableGroup}, and {@link HasTick}. Adds fields and methods
 * for managing tile art, stacked entities, ticking, rendering, and player-over interactions.
 *
 * @provided
 * @stage1
 */
public abstract class Tile extends GameEntity implements PlayerOverHook, RenderableGroup, HasTick {
    private SpriteGroup art;
    /** list of game entities placed on top of the tile */
    private List<GameEntity> stackedEntities;

    /**
     * constructs a tile with specific position and art group
     *
     * @param position the pixel position of the tile
     * @param art sprite group for the tile variants
     *
     */

    public Tile(Positionable position, SpriteGroup art) {
        super(position);
        // pre condition:
        if (position.getX() < 0 || position.getY() < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }
        this.art = art;
        this.stackedEntities = new ArrayList<>();
    }

    /**
     * constructs a tile without an initial art group.
     *
     * @param position pixel position of the tile.
     *
     */

    public Tile(Positionable position) {
        this(position, null);
    }

    /**
     * sets a new art group for the tile.
     *
     * @param art the new sprite group
     *
     */

    public void setArt(SpriteGroup art) {
        this.art = art;
    }

    /**
     * updates the current tile's sprite to a named sprite from its group
     *
     * @param artName the name of the sprite to use
     * @throws ArtNotFoundException if the art group is missing or invalid
     */

    public void updateSprite(String artName) throws ArtNotFoundException {
        if (this.art == null) {
            return; // validation, since I got a "nullpointerexception"
        }

        Sprite newSprite = this.art.getSprite(artName);

        if (this.art != null) {
            this.setSprite(newSprite);

        }
    }


    @Override
    public void tick(EngineState engine, GameState game) {
        super.tick(engine, game);
        stackedEntities.removeIf(GameEntity::isMarkedForRemoval);
        for (GameEntity entity : stackedEntities) {
            entity.tick(engine, game);
        }
    }

    /**
     * returns a copy of the entities currently stacked on this tile
     *
     * @return a list of stacked GameEntity objects.
     *
     */
    public List<GameEntity> getStackedEntities() {
        return new ArrayList<>(stackedEntities);
    }

    /**
     * retrieves all stacked entities that implement PlayerOverhook interface
     *
     * @return list of entities that can react to player standing on them
     *
     */
    public List<PlayerOverHook> getStackedEntitiesWithPlayerOverHook() {
        List<PlayerOverHook> hooks = new ArrayList<>();
        for (GameEntity entity : stackedEntities) {
            if (entity instanceof PlayerOverHook) {
                hooks.add((PlayerOverHook) entity);
            }
        }
        return hooks;
    }

    /**
     * places a new entity on top of the tile
     *
     * @param tile the entity to be placed
     *
     */
    public void placeOn(GameEntity tile) {
        if (tile != null) {
            stackedEntities.add(tile);
        }
    }

    @Override
    public void playerOver(EngineState state, GameState game) {
        for (PlayerOverHook hook : getStackedEntitiesWithPlayerOverHook()) {
            hook.playerOver(state, game);
        }
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(this);

        for (GameEntity entity : stackedEntities) {
            if (entity instanceof Renderable) {
                renderables.add((Renderable) entity);
            }
        }
        return renderables;
    }
}
