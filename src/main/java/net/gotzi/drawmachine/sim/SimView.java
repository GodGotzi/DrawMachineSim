package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.control.layout.HorizontalSplitLayout;
import net.gotzi.drawmachine.sim.editor.SimEditor;
import net.gotzi.drawmachine.sim.monitor.SimMonitorView;
import net.gotzi.drawmachine.sim.main.SimMainView;

import javax.swing.*;

public class SimView extends JPanel implements Simulation {

    private final SimMainView simMainView;
    private final SimMonitorView simMonitor;
    private final SimEditor simEditor;
    private boolean running = false;

    private int timestamp = 0;

    public SimView(SimEditor simEditor) {
        this.simEditor = simEditor;
        this.simMonitor = new SimMonitorView(this);
        this.simMainView = new SimMainView(this);

        add(simMainView.getView());
        add(simMonitor.getView());
        buildLayout();
    }

    /**
     * Create a HorizontalSplitLayout with the main view on the left and the monitor view on the right, and set the width
     * of the monitor view to 120 pixels.
     */
    private void buildLayout() {
        HorizontalSplitLayout horizontalSplitLayout = new HorizontalSplitLayout(simMainView.getView(), simMonitor.getView());
        horizontalSplitLayout.setComponent2Size(120);
        setLayout(horizontalSplitLayout);
    }

    /**
     * > The `run()` function is called when the simulation is started. It sets the `running` variable to `true` and then
     * calls the `render()` function of the `Renderer` class
     */
    @Override
    public void run() {
        this.running = true;
        this.simMainView.getRenderer().render(
                new SimInfo(this.simEditor.getSimValues(), this.simMonitor)
        );
    }

    /**
     * > Stop the simulation and reset the simulation monitor
     */
    @Override
    public void stop() {
        this.running = false;
        this.simMainView.getRenderer().stop();
        this.simMonitor.updateState(new SimRenderState(0, 0));
    }

    /**
     * This function updates the number of steps in the simulation
     *
     * @param state The number of steps that have been completed.
     */
    @Override
    public void updateState(SimRenderState state) {
        this.simMonitor.updateState(state);
        this.timestamp = state.timestamp();
    }

    @Override
    public void resetView() {
        this.simMainView.getMapPanel().getMapLayout().resetView();
    }

    @Override
    public void resetCanvas() {
        this.simMainView.getMapPanel().getSimRenderer().resetCanvas();
    }

    @Override
    public int getTimestamp() {
        return this.timestamp;
    }

    @Override
    public boolean isFastMode() {
        return simMonitor.isFastMode();
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
