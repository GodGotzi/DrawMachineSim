package at.gotzi.drawmachine.sim.view;

import at.gotzi.drawmachine.handler.ResizeHandler;
import at.gotzi.drawmachine.sim.editor.SimEditor;

import javax.swing.*;
import java.awt.*;

public class SimView extends JSplitPane implements Simulation {

    private final SimMainView simMainView;
    private final SimMonitor simMonitor;

    private final SimEditor simEditor;

    private boolean running = false;

    public SimView(SimEditor simEditor) {
        this.simEditor = simEditor;
        this.simMonitor = new SimMonitorView(this);
        this.simMainView = new SimMainView(this);

        build();
        setBackground(Color.GRAY);
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(1);
    }

    private void build() {
        ResizeHandler resizeHandler =
                new ResizeHandler(this, (width, height) -> setDividerLocation(height-110));

        setTopComponent(simMainView);
        setBottomComponent(((SimMonitorView) simMonitor).getView());
        setEnabled(false);
        addComponentListener(resizeHandler);
    }

    public SimMonitor getSimulationMonitor() {
        return simMonitor;
    }

    @Override
    public void run() {
        this.running = true;
        this.simMainView.getRenderer().render(this.simMonitor, this.simEditor);
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
        return this.simMainView.getRenderer().getCurrentSteps();
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
