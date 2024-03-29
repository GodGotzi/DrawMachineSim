/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.control.map;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.utils.ImageUtils;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MapLayout implements MouseListener, MouseMotionListener, MouseWheelListener, LayoutManager2, IMapLayout {

    static final int maxCord = 5000;
    static final int scrollMultiplier = 70;

    private int x;
    private int y;
    private int scroll;

    private MapCopyPanel mapCopyPanel;

    private final int maxScrollSize;

    private final int minScrollSize;

    private final BufferedImage paper;

    private final MapPanel mapPanel;

    public MapLayout(MapPanel mapPanel, BufferedImage paper, int maxScrollSize, int minScrollSize, int startScroll) {
        this.mapPanel = mapPanel;
        this.paper = paper;
        this.x = 0;
        this.y = 0;
        this.maxScrollSize = maxScrollSize;
        this.minScrollSize = minScrollSize;
        this.scroll = startScroll;

        this.buildMapCopyPanel();
        this.updateScroll();
    }

    /**
     * This function creates a new MapCopyPanel object, sets its preferred size, sets its paint action, and adds it to the
     * mapPanel
     */
    private void buildMapCopyPanel() {
        this.mapCopyPanel = new MapCopyPanel();
        this.mapCopyPanel.setPreferredSize(new Dimension(this.scroll, this.scroll));
        this.mapCopyPanel.setPaintAction(this::repaintPanel);
        this.mapPanel.add(mapCopyPanel);
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
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    /**
     * The function takes the size of the parent container (the map panel) and the size of the map copy panel and centers
     * the map copy panel within the map panel
     *
     * @param parent The container that holds the component.
     */
    @Override
    public void layoutContainer(Container parent) {
        Dimension parentDimension = mapPanel.getSize();
        Dimension dimension = mapCopyPanel.getPreferredSize();

        int x = this.x + (parentDimension.width / 2) - (dimension.width / 2);
        int y = this.y + (parentDimension.height / 2) - (dimension.height / 2);
        mapCopyPanel.setBounds(x, y, dimension.width, dimension.height);
    }

    private Point previousMousePoint;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        previousMousePoint = e.getPoint();
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * "If the mouse is dragged, the map is moved in the direction of the mouse drag."
     *
     * The first thing we do is get the current mouse position. Then we subtract the previous mouse position from the
     * current mouse position. This gives us the distance the mouse has moved. We then subtract this distance from the
     * current map position. This gives us the new map position
     *
     * @param e The mouse event that triggered the method.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = e.getPoint();

        this.x -= previousMousePoint.x - point.x;
        this.y -= previousMousePoint.y - point.y;

        if (this.x > 0) this.x = Math.min(MapLayout.maxCord, this.x);
        else this.x = Math.max(-MapLayout.maxCord, this.x);

        if (this.y > 0) this.y = Math.min(MapLayout.maxCord, this.y);
        else this.y = Math.max(-MapLayout.maxCord, this.y);

        previousMousePoint = point;
        layoutContainer(mapPanel);
    }

    @SuppressWarnings("empty")
    @Deprecated
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * If the mouse wheel is moved, update the scroll value and update the scroll.
     *
     * @param e The mouse wheel event.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scroll = this.scroll;
        scroll += e.getPreciseWheelRotation() * MapLayout.scrollMultiplier;

        if (scroll < minScrollSize) scroll = minScrollSize;
        else if (scroll > maxScrollSize) scroll = maxScrollSize;


        if (this.scroll != scroll) {
            this.scroll = scroll;
            updateScroll();
        }
    }

    /**
     * It resizes the image to the size of the scroll pane, and then draws it to the panel
     *
     * @param graphics The graphics object that is used to draw the image.
     */
    private void repaintPanel(Graphics graphics) {
        BufferedImage resizedImage = ImageUtils.resizeImage(paper, scroll, scroll);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(resizedImage, 0, 0, scroll, scroll, null);
    }

    /**
     * This function updates the scroll bar.
     */
    private void updateScroll() {
        this.mapCopyPanel.setPreferredSize(new Dimension(scroll, scroll));
        this.mapCopyPanel.repaint();

        layoutContainer(mapPanel);
    }

    @Override
    public void resetView() {
        this.x = 0;
        this.y = 0;

        layoutContainer(mapPanel);
    }

    @Override
    public void repaint() {
        this.mapCopyPanel.repaint();
    }
}

