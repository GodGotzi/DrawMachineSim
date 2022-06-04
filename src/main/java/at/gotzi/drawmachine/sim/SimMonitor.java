package at.gotzi.drawmachine.sim;

import java.util.concurrent.atomic.AtomicInteger;

public interface SimMonitor {

    AtomicInteger getSimulationSpeed();
    AtomicInteger getSimulationSteps();
    void updateProgress(int progress);

}
