package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimValues;
import net.gotzi.drawmachine.sim.monitor.SimMonitor;

import java.util.concurrent.atomic.AtomicInteger;

public class SimInfo {

    private final SimValues simValues;

    private final int stepAmount;

    private final AtomicInteger speed;

    private final boolean fastMode;

    public SimInfo(SimValues simValues, SimMonitor simMonitor) {
        this.simValues = simValues;
        this.stepAmount = simMonitor.getSimulationSteps().get();
        this.speed = simMonitor.getSimulationSpeed();
        this.fastMode = simMonitor.isFastMode();
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

    public boolean isFastMode() {
        return fastMode;
    }
}
