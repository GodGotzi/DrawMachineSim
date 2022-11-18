package net.gotzi.drawmachine.sim;

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

    private int currentSteps = 0;

    public SimView(SimEditor simEditor) {
        this.simEditor = simEditor;
        this.simMonitor = new SimMonitorView(this);
        this.simMainView = new SimMainView(this);

        add(simMainView);
        add(simMonitor.getPanel());
        buildLayout();
    }

    private void buildLayout() {
        HorizontalSplitLayout horizontalSplitLayout = new HorizontalSplitLayout(simMainView, simMonitor.getPanel());
        horizontalSplitLayout.setComponent2Size(120);
        setLayout(horizontalSplitLayout);
    }

    @Override
    public void run() {
        this.running = true;
        this.simMainView.getRenderer().render(
                new SimInfo(this.simEditor.getTestSimValues(), this.simMonitor)
        );
    }

    @Override
    public void stop() {
        this.running = false;
        this.simMainView.getRenderer().stop();
        this.simMonitor.updateSteps(0);
    }

    @Override
    public void updateSteps(int steps) {
        this.simMonitor.updateSteps(steps);
        this.currentSteps = steps;
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
    public int getCurrentSteps() {
        return this.currentSteps;
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
