package at.gotzi.drawmachine.control;

import at.gotzi.drawmachine.sim.SimRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
    private final Dimension dimension;
    private final IMapLayout mapControlLayout;

    private final SimRenderer simRenderer;

    public MapPanel(Dimension dimension, int maxScrollSize, int minScrollSize, int startScroll) {
        this.dimension = dimension;
        this.simRenderer = new SimRenderer(dimension);
        MapLayout mapLayout = new MapLayout(this, simRenderer.getPaper(), maxScrollSize, minScrollSize, startScroll);


        setBackground(Color.DARK_GRAY);
        setLayout(mapLayout);
        addMouseListener(mapLayout);
        addMouseWheelListener(mapLayout);

        this.mapControlLayout = mapLayout;
    }

    public SimRenderer getSimRenderer() {
        return simRenderer;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public IMapLayout getMapControlLayout() {
        return mapControlLayout;
    }
}