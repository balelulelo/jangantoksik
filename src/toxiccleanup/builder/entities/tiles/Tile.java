package toxiccleanup.builder.entities.tiles;

import toxiccleanup.builder.entities.GameEntity;
import toxiccleanup.builder.entities.PlayerOverHook;
import toxiccleanup.builder.ui.RenderableGroup;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.art.sprites.SpriteGroup;
import toxiccleanup.engine.game.HasTick;
import toxiccleanup.engine.game.Positionable;
import toxiccleanup.engine.renderer.Renderable;

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
public abstract class Tile extends GameEntity implements PlayerOverHook, RenderableGroup, HasTick{
    private SpriteGroup art;
    private List<GameEntity> stackedEntities;

    public Tile (Positionable position, SpriteGroup art){
        super(position);
        // pre condition:
        if(position.getX() < 0 || position.getY() < 0){
            throw new IllegalArgumentException("Position cannot be negative");
        }
        this.art = art;
        this.stackedEntities = new ArrayList<>();
    }
    public Tile(){}

    public void setArt(SpriteGroup art){
        this.art = art;
    }
    public void updateSprite(String artName) throws ArtNotFoundException{

    }
    @Override
    public void tick(){
        stackedEntities.removeIf(GameEntity::isMarkedForRemoval);
        for(GameEntity entity : stackedEntities){
            if(entity instanceof HasTick){
                ((HasTick) entity).tick();
            }
        }
    }
    public List<GameEntity> getStackedEntities(){
        return new ArrayList<>(stackedEntities);
    }
    public List<PlayerOverHook> getStackedEntitiesWithPlayerOverHook(){
        List<PlayerOverHook> hooks = new ArrayList<>();
        for(GameEntity entity : stackedEntities ){
            if(entity instanceof PlayerOverHook){
                hooks.add((PlayerOverHook) entity);
            }
        }
        return hooks;
    }
    public void placeOn(GameEntity tile){
        if(tile != null){
            stackedEntities.add(tile);
        }
    }
    @Override
    public void playerOver(EngineState state, GameState game){
        for(PlayerOverHook hook : getStackedEntitiesWithPlayerOverHook()){
            hook.playerOver(state, game);
        }
    }
    @Override
    public List<Renderable> render(){
        List<Renderable> renderables = new ArrayList<>();
        return renderables;
    }
}
