package net.gotzi.drawmachine.sim.algorithm;

import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.sim.algorithm.logic.FastLogic;
import net.gotzi.drawmachine.sim.algorithm.logic.Logic;
import net.gotzi.drawmachine.sim.algorithm.logic.SimLogic;
import net.gotzi.drawmachine.utils.BenchmarkTimer;

import java.util.ArrayList;
import java.util.List;

public class SimRenderer implements Renderer {

    private final Canvas paper;
    private final Action<Integer> update;

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

            if (!simInfo.isFastMode()) {
                BenchmarkTimer timer = new BenchmarkTimer();
                timer.start();

                Logic logic = new SimLogic(simInfo, update, this.paper, this);

                Thread thread = new Thread(() -> {
                    logic.run();

                    setRunning(false);

                    //TODO Sim Information output

                    System.out.println("Timer ms: " + timer.stop());
                    System.out.println("Travel: " + (logic.getTravelDistance() / 100.0));
                });

                thread.start();
            } else {
                FastLogic fastLogic = new FastLogic(simInfo, update, this.paper, this, simCompletedInfo -> {
                    setRunning(false);

                    //TODO Sim Information output
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
        update(0);
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
