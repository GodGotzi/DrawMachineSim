package net.gotzi.drawmachine.sim.monitor;

import net.gotzi.drawmachine.api.sim.SimRenderState;

import java.util.concurrent.atomic.AtomicInteger;

public interface SimMonitor {

    AtomicInteger getSimulationSpeed();
    AtomicInteger getSimulationSteps();
    void updateProgress(int progress);
    boolean isFastMode();
    void updateState(SimRenderState simRenderState);

}
