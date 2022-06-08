package at.gotzi.drawmachine.sim.monitor;

import java.util.concurrent.atomic.AtomicInteger;

public interface SimMonitor {

    AtomicInteger getSimulationSpeed();
    AtomicInteger getSimulationSteps();
    void updateProgress(int progress);

    void updateSteps(int steps);

}
