package net.gotzi.drawmachine.control.layout;

import java.awt.*;

public class VerticalSplitLayout extends SplitLayout {

    public VerticalSplitLayout(Component component1, Component component2) {
        super(component1, component2);
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void invalidateLayout(Container target) {
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @SuppressWarnings("empty")
    @Deprecated
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
    public void layoutContainer(Container parent) {
        if (size1 != -1) {
            component1.setBounds(0, 0, size1, parent.getHeight());
            component2.setBounds(size1, 0, parent.getWidth()-size1, parent.getHeight());
        } else if (size2 != -1) {
            component1.setBounds(0, 0, parent.getWidth()-size2, parent.getHeight());
            component2.setBounds(parent.getWidth()-size2, 0, size2, parent.getHeight());
        }
    }
}