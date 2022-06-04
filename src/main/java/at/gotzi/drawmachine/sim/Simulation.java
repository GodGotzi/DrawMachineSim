package at.gotzi.drawmachine.sim;

public interface Simulation {

    void run(SimMonitor simMonitor);

    int getCurrentSteps();

    boolean isRunning();

}
