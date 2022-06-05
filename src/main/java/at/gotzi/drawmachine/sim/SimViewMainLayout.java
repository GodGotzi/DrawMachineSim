package at.gotzi.drawmachine.sim;

import javax.swing.*;
import java.awt.*;

public class SimViewMainLayout implements LayoutManager2 {

    private final int min;
    private final int l1;

    private static final int CENTER = 0;
    private static final int TOP = 1;
    private static final int BOTTOM = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    private Component top;
    private Component bottom;
    private Component left;
    private Component right;

    private final Component center;

    public SimViewMainLayout(Container parent, Component center, int min, int l1) {
        this.min = min;
        this.l1 = l1;
        this.center = center;
        this.buildPanels(parent);
    }

    private void buildPanels(Container parent) {
        this.top = new JPanel();
        this.top.setPreferredSize(new Dimension(min, l1));
        parent.add(top);

        this.bottom = new JPanel();
        this.bottom.setPreferredSize(new Dimension(min, l1));
        parent.add(bottom);

        this.left = new JPanel();
        this.left.setPreferredSize(new Dimension(l1, min));
        parent.add(left);

        this.right = new JPanel();
        this.right.setPreferredSize(new Dimension(l1, min));
        parent.add(right);
    }

    private Component getChild(int alignment) {
        return switch (alignment) {
            case CENTER -> center;
            case TOP -> top;
            case BOTTOM -> bottom;
            case LEFT -> left;
            case RIGHT -> right;
            default -> null;
        };
    }

    public void setBorderColor(Color color) {
        this.top.setBackground(color);
        this.bottom.setBackground(color);
        this.left.setBackground(color);
        this.right.setBackground(color);
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

            Component c;

            if ((c=getChild(RIGHT)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c=getChild(LEFT)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c=getChild(CENTER)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c=getChild(TOP)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height;
            }
            if ((c=getChild(BOTTOM)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height;
            }

            Insets insets = parent.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;

            return dim;
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dimension = new Dimension();
        Component c;
        Dimension d;

        for (int i = 1; i < 5; i++) {
            if ((c = getChild(i)) != null) {
                d = c.getMinimumSize();
                dimension.width += d.width;
                dimension.height += d.height;
            }
        }

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

            if (width >= height) {
                center.setBounds(Math.max((width-height)/2, l1), l1, height-l1*2, height-l1*2);

                top.setBounds(0, 0, width, l1);
                bottom.setBounds(0, height-l1, width, l1);
                left.setBounds(0, l1, center.getX(), center.getHeight());
                right.setBounds(width-center.getX(), l1, center.getX(), center.getHeight());
            } else {
                center.setBounds(l1, Math.max((height-width)/2, l1), width-(l1*2), width-(l1*2));

                left.setBounds(0, center.getY(), l1, height-(l1*2));
                right.setBounds(width-center.getX(), center.getY(), l1, height-(l1*2));
                top.setBounds(0, 0, width, center.getY());
                bottom.setBounds(0, height-center.getY(), width, center.getY());
            }
        }
    }
}
