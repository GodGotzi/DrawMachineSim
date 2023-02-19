package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.api.sim.SimPoint;

public abstract class Logic {

    public abstract boolean isFinished(int step);

    public abstract void run();

    protected double distance(SimPoint p1, SimPoint p2) {
        return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
    }
}
