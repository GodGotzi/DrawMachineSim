package at.gotzi.drawmachine.sim;

public interface Simulation {

    void run(SimMonitor simMonitor);

    void resetView();

    int getCurrentSteps();

    boolean isRunning();

}
