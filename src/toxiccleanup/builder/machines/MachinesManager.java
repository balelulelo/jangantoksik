package toxiccleanup.builder.machines;

import toxiccleanup.engine.game.Positionable;

import java.util.ArrayList;
import java.util.List;

public class MachinesManager implements Machines{
    private int power = 14;
    private List<Positionable> teleporter = new ArrayList<>();

    public MachinesManager(){
        // calls the constructor below with power of 14 as default
        this(14);
    }
    public MachinesManager(int power){
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
        if(hasRequiredPower(SolarPanel.COST)){
            adjust(-SolarPanel.COST);
            return new SolarPanel(position);
        } else{
            return null;
        }
    }

    @Override
    public Teleporter spawnTeleporter(Positionable position) {
        return null;
    }

    @Override
    public Positionable getNextTeleporterPosition(Positionable excludedPosition) {
        return null;
    }

    @Override
    public Pump spawnPump(Positionable position, Adjustable adjustable) {
        return null;
    }
}
