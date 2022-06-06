package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.control.ReLayout;

import javax.swing.*;
import java.awt.*;

public class SimViewMainLayout implements LayoutManager2, ReLayout {

    private final int min;
    private final int l1;

    private static final int CENTER = 0;

    private final Component center;

    public SimViewMainLayout(Container parent, Component center, int min, int l1) {
        this.min = min;
        this.l1 = l1;
        this.center = center;
        parent.add(center);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MIN_VALUE);
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
        throw new IllegalArgumentException("You are not supposed to remove some Elements in there");
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);

            Dimension d = center.getPreferredSize();
            dim.width += d.width;
            dim.height = Math.max(d.height, dim.height);

            Insets insets = parent.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;

            return dim;
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dimension = new Dimension();
        Dimension d = center.getMinimumSize();
        dimension.width += d.width;
        dimension.height += d.height;

        return dimension;
    }

    /**
     * > If the width is greater than the height, then the center is a square in the middle of the screen, and the top,
     * bottom, left, and right are rectangles that fill the rest of the screen. If the height is greater than the width,
     * then the center is a square in the middle of the screen, and the top, bottom, left, and right are rectangles that
     * fill the rest of the screen
     *
     * @param parent The container that is being laid out.
     */
    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int width = parent.getWidth();
            int height = parent.getHeight();

            if (width >= height)
                center.setBounds((width-height+l1*2)/2, l1, height-l1*2, height-l1*2);
            else
                center.setBounds(l1, (height-width+l1*2)/2, width-(l1*2), width-(l1*2));
        }
    }

    @Override
    public void reLay() {

    }
}
