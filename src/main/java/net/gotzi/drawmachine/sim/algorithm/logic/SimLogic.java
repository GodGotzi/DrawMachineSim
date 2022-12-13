package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;
import net.gotzi.drawmachine.error.ThreadInterrupt;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.sim.Canvas;
import net.gotzi.drawmachine.sim.SimRenderer;
import net.gotzi.drawmachine.sim.algorithm.SimGCodeLoader;

public class SimLogic implements Logic {

    private final SimInfo simInfo;
    private final SimRenderer simRenderer;
    private final Canvas paper;
    private final Action<Integer> update;
    private final MathLogic mathLogic;
    private SimPoint lastPoint = null;
    private final SimGCodeLoader simGCodeLoader;
    private double travelDistance = 0;
    
    public SimLogic(SimInfo simInfo, Action<Integer> update, Canvas paper, SimRenderer simRenderer) {
        this.paper = paper;
        this.simInfo = simInfo;
        this.simRenderer = simRenderer;
        this.update = update;
        this.mathLogic = new MathLogic(this.simInfo);
        this.simGCodeLoader = new SimGCodeLoader(simInfo.getSimValues().gCode());
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    private void awaitForSpeed() {
        try {
            Thread.sleep(999/simInfo.getSpeed());
        } catch (InterruptedException e) {
            throw new ThreadInterrupt(
                    DrawMachineSim.getInstance()
                            .getWindow(), "Thread couldn't await for correct speed correctly");
        }
    }

    @Override
    public boolean isFinished(int timestamp) {
        return simGCodeLoader.isFinished();
    }

    @Override
    public void run() {
        double stepFactor = this.simInfo.getStepFactor();
        int stepAmount = (int) (simGCodeLoader.calculateTime()/stepFactor);

        for (int timestamp = 1; timestamp <= stepAmount; timestamp++) {
            this.runStep(timestamp);
            this.update.run(timestamp);

            awaitForSpeed();
        }
    }

    /**
     * "Calculate the next point, and if it's on the paper, draw it."
     *
     * The first line of the function is a call to the math logic. It's a call to the `calculatePencilPoint` function,
     * which is defined in the `MathLogic` class
     *
     * @param timestamp the current step of the simulation
     */
    private void runStep(int timestamp) {
        SimPoint simPoint = this.mathLogic.calculatePencilPoint(timestamp, simGCodeLoader);

        if (lastPoint != null)
            travelDistance += Math.sqrt(Math.pow(lastPoint.x() - simPoint.x(), 2) + Math.pow(lastPoint.y() - simPoint.y(), 2));
        lastPoint = simPoint;

        //System.out.println(simPoint);

        try {
            this.paper.setPoint((int) simPoint.x(), (int) simPoint.y());
        } catch (PencilOutOfCanvas ignored) {}
    }
}