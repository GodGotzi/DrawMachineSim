package at.gotzi.drawmachineapp.view.paint;

import at.gotzi.drawmachineapp.DrawMachineApp;

import javax.swing.*;
import java.awt.*;

public class PaintPanel extends JPanel {

    private DrawMachineApp drawMachineApp;

    public PaintPanel(DrawMachineApp drawMachineApp) {
        this.drawMachineApp = drawMachineApp;

        setBackground(Color.BLACK);
        setVisible(true);
        setSize(500, 500);
    }


    public void off() {
        DrawMachineApp.LOGGER.info("disabled");
        setVisible(false);
    }


    public void on() {
        DrawMachineApp.LOGGER.info("enabled");
        setVisible(true);
    }
}
