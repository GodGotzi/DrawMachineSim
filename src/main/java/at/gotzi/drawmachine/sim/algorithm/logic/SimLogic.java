package at.gotzi.drawmachine.sim.algorithm.logic;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.error.PencilOutOfCanvas;
import at.gotzi.drawmachine.error.ThreadInterrupt;
import at.gotzi.drawmachine.sim.SimInfo;
import at.gotzi.drawmachine.sim.api.SimPoint;
import at.gotzi.drawmachine.sim.algorithm.Canvas;
import at.gotzi.drawmachine.sim.algorithm.SimRenderer;

public class SimLogic implements Logic {
    
    private final SimInfo simInfo;
    
    private final SimRenderer simRenderer;

    private final Canvas paper;
    
    private MathLogic mathLogic;
    
    private double realSpeedMiddle;
    
    private double realSpeedM1;
    
    private double realSpeedM2;
    
    public SimLogic(SimInfo simInfo, Canvas paper, SimRenderer simRenderer) {
        this.paper = paper;
        this.simInfo = simInfo;
        this.simRenderer = simRenderer;
        this.calculateRealSpeeds();
        this.buildMathLogic();
    }
    
    /**
     * The function calculates the real speeds of the motors by multiplying the base steps by the speed of the motor and
     * dividing it by the amount of steps
     */
    private void calculateRealSpeeds() {
        realSpeedMiddle = ((double) simInfo.getSimValues().baseSteps() / (double) 
                simInfo.getStepAmount()) * simInfo.getSimValues().speedMiddle();
        realSpeedM1 = ((double) simInfo.getSimValues().baseSteps() / (double) 
                simInfo.getStepAmount()) * simInfo.getSimValues().speedM1();
        realSpeedM2 = ((double) simInfo.getSimValues().baseSteps() / (double)
                simInfo.getStepAmount()) * simInfo.getSimValues().speedM2();
    }
    
    private void buildMathLogic() {
        this.mathLogic = new MathLogic(this);
    }

    protected double getRealSpeedM1() {
        return realSpeedM1;
    }

    protected double getRealSpeedM2() {
        return realSpeedM2;
    }

    protected double getRealSpeedMiddle() {
        return realSpeedMiddle;
    }

    protected SimInfo getSimInfo() {
        return this.simInfo;
    }

    @Override
    // It waits for the correct speed.
    public void awaitForSpeed() {
        try {
            Thread.sleep(999/simInfo.getSpeed());
        } catch (InterruptedException e) {
            throw new ThreadInterrupt(
                    DrawMachineSim.getInstance()
                            .getWindow()
                            .getFrame(), "Thread couldn't await for correct speed correctly");
        }
    }

    @Override
    // It checks if the Simulation is finished
    public boolean isFinished(int step) {
        return !((step <= simInfo.getStepAmount()
                || 1 < mathLogic.speedToDegree(step, realSpeedM1)
                || 1 < mathLogic.speedToDegree(step, realSpeedM2))
                && this.simRenderer.isRunning());
    }

    /**
     * "Calculate the next point, and if it's on the paper, draw it."
     *
     * The first line of the function is a call to the math logic. It's a call to the `calculatePencilPoint` function,
     * which is defined in the `MathLogic` class
     *
     * @param step the current step of the simulation
     */
    @Override
    public void runStep(int step) {
        SimPoint simPoint = this.mathLogic.calculatePencilPoint(step);
        //System.out.println(simPoint);

        try {
            this.paper.setPoint((int) simPoint.x(), (int) simPoint.y());
        } catch (PencilOutOfCanvas ignored) {}
    }
}