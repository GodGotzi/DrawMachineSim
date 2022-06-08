package at.gotzi.drawmachine.sim.algorithm.logic;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.error.PencilOutOfCanvas;
import at.gotzi.drawmachine.error.ThreadInterrupt;
import at.gotzi.drawmachine.sim.SimInfo;
import at.gotzi.drawmachine.sim.SimPoint;
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
    public boolean isFinished(int step) {
        return !((step <= simInfo.getStepAmount()
                || 1 < mathLogic.speedToDegree(step, realSpeedM1)
                || 1 < mathLogic.speedToDegree(step, realSpeedM2))
                && this.simRenderer.isRunning());
    }

    @Override
    public void runStep(int step) {
        SimPoint simPoint = this.mathLogic.calculatePencilPoint(step);
        //System.out.println(simPoint);

        try {
            this.paper.setPoint((int) simPoint.x(), (int) simPoint.y());
        } catch (PencilOutOfCanvas ignored) {}
    }
}
