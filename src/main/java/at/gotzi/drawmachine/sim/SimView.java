package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.view.ResizeHandler;

import javax.swing.*;
import java.awt.*;

public class SimView extends JSplitPane implements Simulation {

    private final SimRenderer renderer;
    private final SimMonitor simMonitor;

    private boolean running = false;

    public SimView() {
        this.renderer = new SimRenderer();
        this.simMonitor = new SimMonitorView(this);

        build();

        setBackground(Color.GRAY);
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(1);
        setResizeWeight(1);
    }

    private void build() {
        ResizeHandler resizeHandler = new ResizeHandler(this, (width, height) -> {
            renderer.setBounds(0, 0, width, (int) (height*0.98));
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
    public int getCurrentSteps() {
        return 0;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
