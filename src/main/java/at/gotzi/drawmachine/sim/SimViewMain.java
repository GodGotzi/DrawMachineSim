package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.control.MapPanel;
import at.gotzi.drawmachine.utils.Helper;

import javax.swing.*;
import java.awt.*;

public class SimViewMain extends JPanel {
    private final MapPanel mapPanel;
    private final SimRenderer renderer;

    public SimViewMain() {
        Dimension paperDimension = Helper.getPaperDimension();
        this.mapPanel = new MapPanel(paperDimension, 3000, 100, 2100);
        this.renderer = mapPanel.getSimRenderer();

        add(mapPanel);
        setBackground(Color.LIGHT_GRAY);
        buildBorderLayout();
    }

    private void buildBorderLayout() {
        SimViewMainLayout simViewMainLayout = new SimViewMainLayout(mapPanel, 5);
        setLayout(simViewMainLayout);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public MapPanel getMapControlPanel() {
        return mapPanel;
    }
}
