package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.sim.algorithm.Renderer;
import net.gotzi.drawmachine.sim.algorithm.logic.FastLogic;
import net.gotzi.drawmachine.sim.algorithm.logic.Logic;
import net.gotzi.drawmachine.sim.algorithm.logic.SimLogic;
import net.gotzi.drawmachine.utils.BenchmarkTimer;

public class SimRenderer implements Renderer {

    private final Canvas paper;
    private final Action<SimRenderState> update;

    private boolean running = false;

    public SimRenderer(Canvas canvas, Action<SimRenderState> update) {
        this.paper = canvas;
        this.update = update;
    }

    /**
     * If the simulation is not running, set it to running, and if it's not in fast mode, run the normal simulation logic,
     * otherwise run the fast simulation logic
     *
     * @param simInfo The information about the simulation.
     */
    @Override
    public void render(SimInfo simInfo) {
        if (!isRunning()) {
            setRunning(true);

            if (!simInfo.isFastMode()) {
                SimLogic logic = new SimLogic(simInfo, update, this.paper);

                Thread thread = new Thread(() -> {
                    logic.run();
                    setRunning(false);

                    SimCompletedInfo simCompletedInfo = logic.getSimCompletedInfo();

                    System.out.println("Timer ms: " + simCompletedInfo.calculationTime());
                    System.out.println("Travel: " + (simCompletedInfo.travelDistance() / 100.0));
                });

                thread.start();
            } else {
                FastLogic fastLogic = new FastLogic(simInfo, update, this.paper, simCompletedInfo -> {
                    setRunning(false);

                    System.out.println("Timer ms: " + simCompletedInfo.calculationTime());
                    System.out.println("Travel: " + (simCompletedInfo.travelDistance() / 100.0));
                });

                Thread thread = new Thread(fastLogic::run);
                thread.start();
            }
        }
    }

    /**
     * If the thread is running, set it to not running and set the current steps to 0.
     */
    public synchronized void stop() {
        setRunning(false);
    }

    /**
     * This function returns the value of the running variable.
     *
     * @return The value of the running variable.
     */
    public synchronized boolean isRunning() {
        return running;
    }

    /**
     * Sets the running variable to the value of the running parameter.
     *
     * @param running This is a boolean value that indicates whether the thread is running or not.
     */
    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * "Reset the canvas and update the display."
     *
     * The first line of the function is a call to the `reset()` function of the `paper` object. This function is defined
     * in the `Paper` class, and it resets the canvas to its initial state
     */
    public void resetCanvas() {
        this.paper.reset();
    }
}
