/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimValues;
import net.gotzi.drawmachine.sim.monitor.SimMonitor;

import java.util.concurrent.atomic.AtomicInteger;

public class SimInfo {

    private final SimValues simValues;

    private final double stepFactor;

    private final AtomicInteger speed;

    private final boolean fastMode;

    public SimInfo(SimValues simValues, SimMonitor simMonitor) {
        this.simValues = simValues;
        this.stepFactor = (double)simMonitor.getSimulationSteps().get()/100.0;
        this.speed = simMonitor.getSimulationSpeed();
        this.fastMode = simMonitor.isFastMode();
    }

    public SimValues getSimValues() {
        return simValues;
    }

    public double getStepFactor() {
        return stepFactor;
    }

    public int getSpeed() {
        return speed.get();
    }

    public boolean isFastMode() {
        return fastMode;
    }
}
