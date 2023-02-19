package net.gotzi.drawmachine.control.map;

import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.api.sim.SimState;
import net.gotzi.drawmachine.sim.SimRenderer;
import net.gotzi.drawmachine.sim.Canvas;
import net.gotzi.drawmachine.sim.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public class MapPanel extends JPanel {
    private final Dimension dimension;
    private final IMapLayout mapLayout;
    private final SimRenderer simRenderer;

    private final Simulation simulation;

    public MapPanel(Dimension dimension, Simulation simulation, int maxScrollSize, int minScrollSize, int startScroll) {
        this.dimension = dimension;
        
        Canvas paper = new Canvas(dimension.width, dimension.height, Color.BLACK);

        this.simRenderer = new SimRenderer(paper, this::updateState);
        this.mapLayout = new MapLayout(this, paper, maxScrollSize, minScrollSize, startScroll);
        this.simulation = simulation;
        build();
    }

    /**
     * This function sets the background color of the JPanel to dark gray, sets the layout to the mapLayout, adds the mouse
     * listeners to the mapLayout, and adds the mouse wheel listener to the mapLayout
     */
    private void build() {
        setBackground(Color.DARK_GRAY);
        setLayout((LayoutManager) mapLayout);
        addMouseListener((MouseListener) mapLayout);
        addMouseMotionListener((MouseMotionListener) mapLayout);
        addMouseWheelListener((MouseWheelListener) mapLayout);
    }

    private void updateState(SimRenderState state) {
        mapLayout.repaint();
        this.simulation.updateState(state);
    }

    public SimRenderer getSimRenderer() {
        return simRenderer;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public IMapLayout getMapLayout() {
        return mapLayout;
    }
}