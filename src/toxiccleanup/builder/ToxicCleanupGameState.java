package toxiccleanup.builder;

import toxiccleanup.builder.machines.Machines;
import toxiccleanup.builder.player.Player;
import toxiccleanup.builder.player.PlayerManager;
import toxiccleanup.builder.world.World;

public class ToxicCleanupGameState implements GameState {
    private PlayerManager player;
    private World world;
    private Machines machines;

    // at stage 0, these were only PlayerManager player.
    public ToxicCleanupGameState(PlayerManager player){
        this.player = player;
    }
    // at stage 2, we add Machines and World.
    public ToxicCleanupGameState(World world, PlayerManager player, Machines machines){
        this.player = player;
        this.world = world;
        this.machines = machines;
    }

    @Override
    public World getWorld(){
        return world;
    }
    @Override
    public Player getPlayer(){
        return player;
    }
    @Override
    public Machines getMachines(){
        return machines;
    }


}
