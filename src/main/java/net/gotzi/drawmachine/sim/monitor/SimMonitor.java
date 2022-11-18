package net.gotzi.drawmachine.sim.monitor;

import java.util.concurrent.atomic.AtomicInteger;

public interface SimMonitor {

    AtomicInteger getSimulationSpeed();
    AtomicInteger getSimulationSteps();
    void updateProgress(int progress);
    boolean isFastMode();
    void updateSteps(int steps);

}
