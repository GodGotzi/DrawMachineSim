package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.api.sim.SimValues;
import at.gotzi.drawmachine.sim.monitor.SimMonitor;

import java.util.concurrent.atomic.AtomicInteger;

public class SimInfo {

    private final SimValues simValues;

    private int stepAmount;

    private AtomicInteger speed;

    public SimInfo(SimValues simValues, SimMonitor simMonitor) {
        this.simValues = simValues;
        this.stepAmount = simMonitor.getSimulationSteps().get();
        this.speed = simMonitor.getSimulationSpeed();
    }

    public SimValues getSimValues() {
        return simValues;
    }

    public int getStepAmount() {
        return stepAmount;
    }

    public int getSpeed() {
        return speed.get();
    }
}
