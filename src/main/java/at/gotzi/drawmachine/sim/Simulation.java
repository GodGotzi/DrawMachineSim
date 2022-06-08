package at.gotzi.drawmachine.sim;

public interface Simulation {

    void run();

    void stop();

    void resetView();

    void resetCanvas();

    int getCurrentSteps();

    boolean isRunning();

    void updateSteps(int step);

}
