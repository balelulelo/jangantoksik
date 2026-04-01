package toxiccleanup.builder.machines;

import toxiccleanup.engine.game.Positionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * manages the placement and power consumption of game machines.
 */

public class MachinesManager implements Machines {
    /** current power level of the machine system. */
    private int power = 14;
    /** the positions of all active teleporters. */
    private List<Positionable> teleporter = new ArrayList<>();

    /**
     * constructs a machines manager with the default power of 14
     */
    public MachinesManager() {
        // calls the constructor below with power of 14 as default
        this(14);
    }

    /**
     * constructs a machines manager with a specific initial power
     *
     * @param power starting power lever
     */
    public MachinesManager(int power) {
        this.power = Math.clamp(power, 0, 14);
    }

    @Override
    public boolean hasRequiredPower(int powerRequirement) {
        return power >= powerRequirement;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void setPower(int value) {
        power = Math.clamp(value, 0, getMaxPower());
    }

    @Override
    public int getMaxPower() {
        return 14;
    }

    @Override
    public void adjust(int amount) {
        // calculates the power and limit it minimum 0 and maximum 14 (clamp)
        // (current value, min, max)
        // if adding a power (+), when building an object (- in amount)
        power = Math.clamp(this.power + amount, 0, getMaxPower());
    }

    // from this line below, only edit later at stage 3 ============
    @Override
    public SolarPanel spawnSolarPanel(Positionable position) {
        // solar panel costs 3 power to use
        if (hasRequiredPower(SolarPanel.COST)) {
            adjust(-SolarPanel.COST);
            return new SolarPanel(position);
        } else {
            return null;
        }
    }

    @Override
    public Teleporter spawnTeleporter(Positionable position) {

        if (hasRequiredPower(Teleporter.COST)) {
            adjust(-Teleporter.COST);
            // "records the teleporter's position for future"
            teleporter.add(position);
            return new Teleporter(position);
        } else {
            return null;
        }
    }

    @Override
    public Positionable getNextTeleporterPosition(Positionable excludedPosition) {
        // can't teleport because no teleporter available if less than 1
        if (teleporter.size() <= 1) {
            return null;
        }
        // saves all possible destinations except excludedPosition
        List<Positionable> destinations = new ArrayList<>(teleporter);
        destinations.remove(excludedPosition);

        // generates random numbers for teleporter location
        Random random = new Random();
        int randomLocation = random.nextInt(destinations.size());
        return destinations.get(randomLocation);
    }

    @Override
    public Pump spawnPump(Positionable position, Adjustable adjustable) {
        // pump costs 5 power to use
        if (hasRequiredPower(Pump.COST)) {
            adjust(-Pump.COST);
            return new Pump(position, adjustable);
        }
        return null;
    }
}
