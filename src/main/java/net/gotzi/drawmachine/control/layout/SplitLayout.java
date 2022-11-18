package net.gotzi.drawmachine.control.layout;

import java.awt.*;

public abstract class SplitLayout implements LayoutManager2 {
    protected final Component component1;
    protected final Component component2;

    protected int size1 = -1;
    protected int size2 = -1;

    public SplitLayout(Component component1, Component component2) {
        this.component1 = component1;
        this.component2 = component2;
    }

    public void setComponent1Size(int size) {
        this.size1 = size;
    }

    public void setComponent2Size(int size) {
        this.size2 = size;
    }
}