package at.gotzi.drawmachine.view.paint;

import at.gotzi.drawmachine.DrawMachineCA;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {

    private DrawMachineCA drawMachineCA;

    public PaintPanel(DrawMachineCA drawMachineCA) {
        this.drawMachineCA = drawMachineCA;

        setBackground(Color.BLACK);
        setVisible(true);
        setSize(500, 500);
    }


    public void off() {
        DrawMachineCA.LOGGER.info("disabled");
        setVisible(false);
    }


    public void on() {
        DrawMachineCA.LOGGER.info("enabled");
        setVisible(true);
    }
}
