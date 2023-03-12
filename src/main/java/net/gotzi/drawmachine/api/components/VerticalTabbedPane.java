/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.api.components;

import javax.swing.*;
import java.awt.*;

public class VerticalTabbedPane extends JTabbedPane {

    public VerticalTabbedPane() {
        super(JTabbedPane.LEFT);
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);

        JLabel tabComponent = createVerticalLabel(title);
        super.setTabComponentAt(super.getTabCount()-1, tabComponent);
    }

    private JLabel createVerticalLabel(String text) {
        JLabel lab = new JLabel(text);
        lab.setUI(new VerticalLabelUI(false));
        return lab;
    }
}
