package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;

public class FastLogic implements Logic {

    private final Action<SimCompletedInfo> finished;

    public FastLogic(Action<SimCompletedInfo> finished) {
        this.finished = finished;
    }

    @Override
    public boolean isFinished(int step) {
        return false;
    }

    @Override
    public void run() {



        finished.run(null);
    }

    @Override
    public double getTravelDistance() {
        return 0;
    }
}
