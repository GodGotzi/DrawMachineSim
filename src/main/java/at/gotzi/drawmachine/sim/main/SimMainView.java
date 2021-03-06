package at.gotzi.drawmachine.sim.main;

import at.gotzi.drawmachine.control.map.MapPanel;
import at.gotzi.drawmachine.sim.Simulation;
import at.gotzi.drawmachine.sim.algorithm.Renderer;
import at.gotzi.drawmachine.sim.algorithm.SimRenderer;
import at.gotzi.drawmachine.utils.Helper;

import javax.swing.*;
import java.awt.*;

public class SimMainView extends JPanel {
    private final MapPanel mapPanel;
    private final SimRenderer renderer;

    public SimMainView(Simulation simulation) {
        Dimension paperDimension = Helper.getPaperDimension();
        this.mapPanel = new MapPanel(paperDimension, simulation, 3000, 100, 1000);
        this.renderer = mapPanel.getSimRenderer();

        add(mapPanel);
        setBackground(Color.LIGHT_GRAY);
        buildBorderLayout();
    }

    private void buildBorderLayout() {
        SimMainViewLayout simMainViewLayout = new SimMainViewLayout(mapPanel, 5);
        setLayout(simMainViewLayout);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }
}
