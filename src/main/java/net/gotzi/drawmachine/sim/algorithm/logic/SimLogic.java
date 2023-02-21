package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;
import net.gotzi.drawmachine.error.ThreadInterrupt;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.sim.Canvas;
import net.gotzi.drawmachine.sim.SimRenderer;
import net.gotzi.drawmachine.sim.algorithm.SimGCodeLoader;
import net.gotzi.drawmachine.utils.BenchmarkTimer;

public class SimLogic extends Logic {

    private final SimInfo simInfo;
    private final Canvas paper;
    private final Action<SimRenderState> update;
    private final MathLogic mathLogic;
    private final SimGCodeLoader simGCodeLoader;
    private SimCompletedInfo simCompletedInfo;
    private SimRenderer simRenderer;
    private double travelDistance = 0;
    
    public SimLogic(SimInfo simInfo, SimRenderer simRenderer, Action<SimRenderState> update, Canvas paper) {
        this.simInfo = simInfo;
        this.update = update;
        this.paper = paper;
        this.simRenderer = simRenderer;
        this.mathLogic = new MathLogic(this.simInfo);
        this.simGCodeLoader = new SimGCodeLoader(simInfo.getSimValues().gCode());
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
        long nativeTime = simGCodeLoader.getFullTime();
        double time = (double)nativeTime * stepFactor;

        SimPoint lastPoint = null;

        BenchmarkTimer timer = new BenchmarkTimer();
        timer.start();

        System.out.println("start " + time + " " + stepFactor + " " + nativeTime);
        for (int timestamp = 1; timestamp <= time && simRenderer.isRunning(); timestamp++) {

            lastPoint = this.runStep(((double)timestamp/ stepFactor), lastPoint);
            this.update.run(new SimRenderState((int) (timestamp/stepFactor), (int) nativeTime));

            awaitForSpeed();
        }

        this.simCompletedInfo = new SimCompletedInfo(timer.stop(), travelDistance);
    }

    /**
     * "Calculate the next point, and if it's on the paper, draw it."
     *
     * The first line of the function is a call to the math logic. It's a call to the `calculatePencilPoint` function,
     * which is defined in the `MathLogic` class
     *
     * @param timestamp the current step of the simulation
     */
    private SimPoint runStep(double timestamp, SimPoint lastPoint) {
        SimPoint simPoint = this.mathLogic.calculatePencilPoint(timestamp, simGCodeLoader);

        if (lastPoint != null)
            travelDistance += Math.sqrt(Math.pow(lastPoint.x() - simPoint.x(), 2) + Math.pow(lastPoint.y() - simPoint.y(), 2));

        try {
            this.paper.setPoint((int) simPoint.x(), (int) simPoint.y());
        } catch (PencilOutOfCanvas ignored) {}

        return simPoint;
    }

    public SimCompletedInfo getSimCompletedInfo() {
        return simCompletedInfo;
    }
}