package net.gotzi.drawmachine.sim.algorithm.logic;

public interface Logic {

    boolean isFinished(int step);

    void run();

    double getTravelDistance();

}
