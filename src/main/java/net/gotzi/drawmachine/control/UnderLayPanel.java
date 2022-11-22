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

    public void setBorderColor(Color color) {
        this.color = color;

        this.panelN.setBackground(color);
        this.panelE.setBackground(color);
        this.panelS.setBackground(color);
        this.panelW.setBackground(color);
    }

    public Color getColor() {
        return color;
    }
}
