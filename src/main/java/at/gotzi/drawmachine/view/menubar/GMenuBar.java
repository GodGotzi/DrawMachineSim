package at.gotzi.drawmachine.view.menubar;

import at.gotzi.drawmachine.control.ControlComponent;

import javax.swing.*;
import java.awt.*;

public class GMenuBar extends JMenuBar implements ControlComponent {

    public GMenuBar() {
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(new Color(60, 87, 183), 2));
        setSize(getWidth(), 250);
    }

    @Override
    public JMenu add(JMenu c) {
        c.setFont(c.getFont().deriveFont(15.0f));
        return super.add(c);
    }

    @Override
    public void sizing(GroupLayout groupLayout) {

    }
}