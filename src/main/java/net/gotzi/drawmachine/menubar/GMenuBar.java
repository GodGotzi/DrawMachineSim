package net.gotzi.drawmachine.menubar;

import javax.swing.*;
import java.awt.*;

public class GMenuBar extends JMenuBar {

    public GMenuBar() {
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(new Color(126, 60, 183), 2));
        setSize(getWidth(), 250);
    }

    @Override
    public JMenu add(JMenu c) {
        c.getPopupMenu().setBackground(new Color(126, 60, 183));
        c.getPopupMenu().setForeground(new Color(126, 60, 183));
        c.setBackground(Color.LIGHT_GRAY);
        c.setForeground(Color.WHITE);
        c.setFont(c.getFont().deriveFont(15.0f));
        return super.add(c);
    }
}