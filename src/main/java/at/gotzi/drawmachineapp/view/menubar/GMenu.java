package at.gotzi.drawmachineapp.view.menubar;

import javax.swing.*;
import java.awt.*;

public class GMenu extends JMenu {

    public GMenu(String name) {
        super(name);
    }

    @Override
    public JMenuItem add(JMenuItem jMenuItem) {
        jMenuItem.setBackground(Color.BLACK);
        jMenuItem.setForeground(Color.WHITE);
        return super.add(jMenuItem);
    }
}