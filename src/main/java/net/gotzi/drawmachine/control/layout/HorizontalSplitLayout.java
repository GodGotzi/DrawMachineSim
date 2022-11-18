package net.gotzi.drawmachine.control.layout;

import java.awt.*;

public class HorizontalSplitLayout extends SplitLayout {

    public HorizontalSplitLayout(Component component1, Component component2) {
        super(component1, component2);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {

    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        if (size1 != -1) {
            component1.setBounds(0, 0, parent.getWidth(), size1);
            component2.setBounds(0, size1, parent.getWidth(), parent.getHeight()-size1);
        } else if (size2 != -1) {
            component1.setBounds(0, 0, parent.getWidth(), parent.getHeight()-size2);
            component2.setBounds(0, parent.getHeight()-size2, parent.getWidth(), size2);
        }
    }
}
