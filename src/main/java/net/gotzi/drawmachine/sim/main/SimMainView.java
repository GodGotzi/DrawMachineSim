/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.main;

import net.gotzi.drawmachine.control.DimensionConstants;
import net.gotzi.drawmachine.control.map.MapPanel;
import net.gotzi.drawmachine.sim.Simulation;
import net.gotzi.drawmachine.sim.algorithm.Renderer;
import net.gotzi.drawmachine.sim.SimRenderer;

import javax.swing.*;
import java.awt.*;

public class SimMainView {

    private final JPanel view;
    private final MapPanel mapPanel;
    private final SimRenderer renderer;

    public SimMainView(Simulation simulation) {
        Dimension paperDimension = DimensionConstants.getConstantDimension("simulation.view.canvas");

        this.view = new JPanel();

        this.mapPanel = new MapPanel(paperDimension, simulation, 3000, 100, 1000);
        this.renderer = mapPanel.getSimRenderer();

        this.view.add(mapPanel);
        this.view.setBackground(Color.LIGHT_GRAY);

        SimMainViewLayout simMainViewLayout = new SimMainViewLayout(mapPanel, 5);
        this.view.setLayout(simMainViewLayout);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public JPanel getView() {
        return view;
    }
}
