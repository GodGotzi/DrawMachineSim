package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.handler.ResizeHandler;

import javax.swing.*;
import java.awt.*;

public class SimView extends JSplitPane implements Simulation {

    private final SimViewMain renderer;
    private final SimMonitor simMonitor;

    private boolean running = false;

    public SimView() {
        this.renderer = new SimViewMain();
        this.simMonitor = new SimMonitorView(this);

        build();

        setBackground(Color.GRAY);
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(1);
        setResizeWeight(1);
    }

    private void build() {
        ResizeHandler resizeHandler = new ResizeHandler(this, (width, height) -> {
            setDividerLocation(height-100);
        });

        setTopComponent(renderer);
        setBottomComponent(((SimMonitorView) simMonitor).getView());
        setEnabled(false);
        addComponentListener(resizeHandler);
    }

    public SimMonitor getSimulationMonitor() {
        return simMonitor;
    }

    @Override
    public void run(SimMonitor simMonitor) {
        this.running = true;
    }

    @Override
    public void resetView() {
        this.renderer.getMapControlPanel().getMapControlLayout().resetView();
    }

    @Override
    public int getCurrentSteps() {
        return 0;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
