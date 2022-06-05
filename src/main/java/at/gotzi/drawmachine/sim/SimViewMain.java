package at.gotzi.drawmachine.sim;

import javax.swing.*;
import java.awt.*;

public class SimViewMain extends JPanel implements Renderer {
    private final JPanel drawPanel;

    public SimViewMain() {
        this.drawPanel = new JPanel();
        this.drawPanel.setBackground(Color.WHITE);

        buildBorderLayout();
    }

    private void buildBorderLayout() {
        SimViewMainLayout simViewMainLayout = new SimViewMainLayout(this, drawPanel,300, 4);
        simViewMainLayout.setBorderColor(Color.LIGHT_GRAY);
        setLayout(simViewMainLayout);
    }

    @Override
    public void render() {

    }

    @Override
    public void nextStep() {

    }
}
