package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimValues;
import net.gotzi.drawmachine.sim.monitor.SimMonitor;

import java.util.concurrent.atomic.AtomicInteger;

public class SimInfo {

    private final SimValues simValues;

    private final int stepAmount;

    private final AtomicInteger speed;

    private double realSpeedMiddle;

    private double realSpeedM1;

    private double realSpeedM2;

    private final boolean fastMode;

    public SimInfo(SimValues simValues, SimMonitor simMonitor) {
        this.simValues = simValues;
        this.stepAmount = simMonitor.getSimulationSteps().get();
        this.speed = simMonitor.getSimulationSpeed();
        this.fastMode = simMonitor.isFastMode();
        this.calculateRealSpeeds();
    }

    /**
     * The function calculates the real speeds of the motors by multiplying the base steps by the speed of the motor and
     * dividing it by the amount of steps
     */
    private void calculateRealSpeeds() {
        realSpeedMiddle = ((double) this.simValues.baseSteps() / (double)
                this.stepAmount) * this.simValues.speedMiddle();
        realSpeedM1 = ((double) this.simValues.baseSteps() / (double)
                this.stepAmount) * this.simValues.speedM1();
        realSpeedM2 = ((double) this.simValues.baseSteps() / (double)
                this.stepAmount) * this.simValues.speedM2();
    }

    public SimValues getSimValues() {
        return simValues;
    }

    public int getStepAmount() {
        return stepAmount;
    }

    public double getRealSpeedM1() {
        return realSpeedM1;
    }

    public double getRealSpeedM2() {
        return realSpeedM2;
    }

    public double getRealSpeedMiddle() {
        return realSpeedMiddle;
    }

    public int getSpeed() {
        return speed.get();
    }

    public boolean isFastMode() {
        return fastMode;
    }
}
