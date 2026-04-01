package toxiccleanup.builder;

import toxiccleanup.builder.machines.Machines;
import toxiccleanup.builder.player.Player;
import toxiccleanup.builder.player.PlayerManager;
import toxiccleanup.builder.world.World;

/**
 * class that encapsulate the current state of game for processing
 *
 */

public class ToxicCleanupGameState implements GameState {
    /** handles player state. */
    private PlayerManager player;
    /** the game world contain tiles. */
    private World world;
    /** handles machine operations. */
    private Machines machines;

    /**
     * basic constructor for initial game stage
     *
     * @param player instance of player manager.<br>
     *
     * note: stage 0
     */
    public ToxicCleanupGameState(PlayerManager player) {
        this.player = player;
    }

    /**
     * full constructor for advanced game stages
     *
     * @param player the player manager
     * @param machines the machine manager
     * @param world the game world.<br>
     *
     * note: stage 2
     */
    public ToxicCleanupGameState(World world, PlayerManager player, Machines machines) {
        this.player = player;
        this.world = world;
        this.machines = machines;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Machines getMachines() {
        return machines;
    }


}
