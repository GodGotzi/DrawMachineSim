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

    public void resetCanvas() {
        this.paper.reset();
        update.run(0);
    }
}
