package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.control.MapPanel;
import at.gotzi.drawmachine.utils.Helper;

import javax.swing.*;
import java.awt.*;

public class SimViewMain extends JPanel {
    private final MapPanel<SimRenderer> mapPanel;
    private final SimRenderer renderer;

    public SimViewMain() {
        Dimension paperDimension = Helper.getPaperDimension();
        this.renderer = new SimRenderer(paperDimension);
        this.renderer.setPreferredSize(paperDimension);
        this.mapPanel = new MapPanel<>(paperDimension, 10);
        this.mapPanel.setComponent(renderer);

        setBackground(Color.LIGHT_GRAY);
        buildBorderLayout();
    }

    private void buildBorderLayout() {
        SimViewMainLayout simViewMainLayout = new SimViewMainLayout(this, mapPanel,300, 5);
        setLayout(simViewMainLayout);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public MapPanel<SimRenderer> getMapControlPanel() {
        return mapPanel;
    }
}
