package at.gotzi.drawmachine.sim.alogrithm;

import at.gotzi.drawmachine.api.Action;
import at.gotzi.drawmachine.sim.editor.SimEditor;
import at.gotzi.drawmachine.sim.view.SimMonitor;

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
    public void render(SimMonitor simMonitor, SimEditor simEditor) {
        if (!isRunning()) {
            running = true;

            Thread thread = new Thread(() -> {
                for (int i = 1; i <= simMonitor.getSimulationSteps().get() && isRunning(); i++) {
                    try {
                        Thread.sleep(999/simMonitor.getSimulationSpeed().get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (isRunning())
                        update(i);
                }
            });

            thread.start();
        }
    }

    public void stop() {
        running = false;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void update(int step) {
        this.update.run(step);
    }

    protected void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    public int getCurrentSteps() {
        return this.currentSteps;
    }

    public void resetCanvas() {
        this.paper.reset();
        update.run(0);
    }
}
