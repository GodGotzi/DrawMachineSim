package net.gotzi.drawmachine.control;

import javax.swing.*;
import java.awt.*;

public class UnderLayPanel extends JPanel {

    private JPanel panelN;
    private JPanel panelE;
    private JPanel panelS;
    private JPanel panelW;

    private Color color = super.getBackground();

    public UnderLayPanel(Component component) {
        BorderLayout borderLayout = new BorderLayout();

        setLayout(borderLayout);
        add(component, BorderLayout.CENTER);
    }

    // Creating a border around the component.
    public void setNorthBorderThickness(int thickness) {
        this.panelN = new JPanel();
        this.panelN.setPreferredSize(new Dimension(-1, thickness));
        add(this.panelN, BorderLayout.NORTH);
    }

    public void setEastBorderThickness(int thickness) {
        this.panelE = new JPanel();
        this.panelE.setPreferredSize(new Dimension(thickness, -1));
        add(this.panelE, BorderLayout.EAST);
    }

    public void setSouthBorderThickness(int thickness) {
        this.panelS = new JPanel();
        this.panelS.setPreferredSize(new Dimension(-1, thickness));
        add(this.panelS, BorderLayout.SOUTH);
    }

    public void setWestBorderThickness(int thickness) {
        this.panelW = new JPanel();
        this.panelW.setPreferredSize(new Dimension(thickness, -1));
        add(this.panelW, BorderLayout.WEST);
    }

    /**
     * This function sets the color of the border of the panel.
     *
     * @param color The color of the border.
     */
    public void setBorderColor(Color color) {
        this.color = color;

        this.panelN.setBackground(color);
        this.panelE.setBackground(color);
        this.panelS.setBackground(color);
        this.panelW.setBackground(color);
    }

    /**
     * > Sets the opaque property of the panelN, panelE, panelS, panelW, and the current panel to the value of the opaque
     * parameter
     *
     * @param opaque true if the component should be opaque, false otherwise
     */
    public void setOpaqueAll(boolean opaque) {
        this.panelN.setOpaque(opaque);
        this.panelE.setOpaque(opaque);
        this.panelS.setOpaque(opaque);
        this.panelW.setOpaque(opaque);
        super.setOpaque(opaque);
    }

    public Color getColor() {
        return color;
    }
}
