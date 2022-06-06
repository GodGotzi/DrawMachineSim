package at.gotzi.drawmachine.control;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.utils.Helper;

import java.awt.*;
import java.awt.event.*;

public class MapControlLayout implements MouseListener, MouseWheelListener, LayoutManager2, MapLayout {

    private Component component;

    private int x;
    private int y;

    private int border;
    private final MapControlPanel<? extends Component> mapControlPanel;

    public MapControlLayout(MapControlPanel<? extends Component>  mapControlPanel, int border) {
        this.mapControlPanel = mapControlPanel;
        this.x = 0;
        this.y = 0;
        this.border = border;
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
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
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
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
        Dimension parentDimension = mapControlPanel.getSize();
        Dimension dimension = component.getPreferredSize();

        int x = this.x + (parentDimension.width/2) - (dimension.width/2);
        int y = this.y + (parentDimension.height/2) - (dimension.height/2);
        component.setBounds(x, y, dimension.width, dimension.height);
        Helper.printClassMethodName();
    }

    private Point previousMousePoint;

    @Override
    public void mouseClicked(MouseEvent e) {
        Helper.printClassMethodName();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        previousMousePoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point point = e.getPoint();

        this.x -= previousMousePoint.x - point.x;
        this.y -= previousMousePoint.y - point.y;

        layoutContainer(mapControlPanel);
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void resetView() {
        this.x = 0;
        this.y = 0;
        layoutContainer(mapControlPanel);
    }
}
