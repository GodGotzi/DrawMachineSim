package at.gotzi.drawmachine.sim.algorithm.logic;

public interface Logic {

    boolean isFinished(int step);

    void awaitForSpeed();

    void runStep(int step);

}
