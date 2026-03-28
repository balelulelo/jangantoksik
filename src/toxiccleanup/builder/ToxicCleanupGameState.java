package toxiccleanup.builder;

import toxiccleanup.builder.machines.Machines;
import toxiccleanup.builder.player.Player;
import toxiccleanup.builder.player.PlayerManager;
import toxiccleanup.builder.world.World;

public class ToxicCleanupGameState implements GameState {
    private PlayerManager playerManager;
    private World world;
    private Machines machines;


    public ToxicCleanupGameState(PlayerManager player){
        this.playerManager = player;
        this.world = null;
        this.machines = null;
    }
    @Override
    public World getWorld(){
        return world;
    }
    @Override
    public Player getPlayer(){
        return playerManager;
    }
    @Override
    public Machines getMachines(){
        return machines;
    }


}
