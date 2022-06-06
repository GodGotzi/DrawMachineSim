package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.control.MapControlPanel;
import at.gotzi.drawmachine.utils.Helper;

import javax.swing.*;
import java.awt.*;

public class SimViewMain extends JPanel {
    private final MapControlPanel<SimRenderer> mapControlPanel;
    private final SimRenderer renderer;

    public SimViewMain() {
        Dimension paperDimension = Helper.getPaperDimension();
        this.renderer = new SimRenderer(paperDimension);
        this.renderer.setPreferredSize(paperDimension);
        this.mapControlPanel = new MapControlPanel<>(paperDimension);
        this.mapControlPanel.setComponent(renderer);

        setBackground(Color.LIGHT_GRAY);
        buildBorderLayout();
    }

    private void buildBorderLayout() {
        SimViewMainLayout simViewMainLayout = new SimViewMainLayout(this, mapControlPanel,300, 5);
        setLayout(simViewMainLayout);
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
