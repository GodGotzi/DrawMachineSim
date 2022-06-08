package at.gotzi.drawmachine.sim.algorithm;

import at.gotzi.drawmachine.api.Action;
import at.gotzi.drawmachine.sim.SimInfo;
import at.gotzi.drawmachine.sim.algorithm.logic.Logic;
import at.gotzi.drawmachine.sim.algorithm.logic.SimLogic;

public class SimRenderer implements Renderer {

    private final Canvas paper;
    private final Action<Integer> update;

    private int currentSteps = 0;

    private boolean running = false;

    public SimRenderer(Canvas canvas, Action<Integer> update) {
        this.paper = canvas;
        this.update = update;
    }

    /**
     * It creates a new thread that runs the simulation logic, and updates the UI every step
     *
     * @param simInfo The information about the simulation.
     */
    @Override
    public void render(SimInfo simInfo) {
        if (!isRunning()) {
            setRunning(true);
            Logic logic = new SimLogic(simInfo, this.paper, this);

            Thread thread = new Thread(() -> {
                for (int step = 1; !logic.isFinished(step); step++) {
                    logic.runStep(step);

                    update(step);
                    setCurrentSteps(step);

                    logic.awaitForSpeed();
                }

                setRunning(false);
            });

            thread.start();
        }
    }

    /**
     * If the thread is running, set it to not running and set the current steps to 0.
     */
    public synchronized void stop() {
        setRunning(false);
        setCurrentSteps(0);
    }

    @Override
    public int getCurrentSteps() {
        return this.currentSteps;
    }

    private synchronized void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    private synchronized void setRunning(boolean running) {
        this.running = running;
    }

    private synchronized void update(int step) {
        this.update.run(step);
    }

    /**
     * "Reset the canvas and update the display."
     *
     * The first line of the function is a call to the `reset()` function of the `paper` object. This function is defined
     * in the `Paper` class, and it resets the canvas to its initial state
     */
    public void resetCanvas() {
        this.paper.reset();
        update.run(0);
    }
}
