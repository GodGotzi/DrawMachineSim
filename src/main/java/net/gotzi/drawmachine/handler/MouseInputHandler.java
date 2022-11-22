package net.gotzi.drawmachine.handler;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseInputHandler implements MouseInputListener {

    private static final int[] cursorMapping = new int[]
            { Cursor.NW_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR,
                    Cursor.NE_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
                    Cursor.NW_RESIZE_CURSOR, 0, 0, 0, Cursor.NE_RESIZE_CURSOR,
                    Cursor.W_RESIZE_CURSOR, 0, 0, 0, Cursor.E_RESIZE_CURSOR,
                    Cursor.SW_RESIZE_CURSOR, 0, 0, 0, Cursor.SE_RESIZE_CURSOR,
                    Cursor.SW_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR,
                    Cursor.SE_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
            };

    private static final int BORDER_DRAG_THICKNESS = 5;
    private static final int CORNER_DRAG_WIDTH = 16;
    private final JRootPane rootPane;
    private final Window window;

    public MouseInputHandler(Window window, JRootPane rootPane) {
        this.rootPane = rootPane;
        this.window = window;
    }

    /**
     * Set to true if the drag operation is moving the window.
     */
    private boolean isMovingWindow;

    /**
     * Used to determine the corner the resize is occurring from.
     */
    private int dragCursor;

    /**
     * X location the mouse went down on for a drag operation.
     */
    private int dragOffsetX;

    /**
     * Y location the mouse went down on for a drag operation.
     */
    private int dragOffsetY;

    /**
     * Width of the window when the drag started.
     */
    private int dragWidth;

    /**
     * Height of the window when the drag started.
     */
    private int dragHeight;

    private Cursor lastCursor;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent ev) {
        JRootPane rootPane = this.rootPane;

        Point dragWindowOffset = ev.getPoint();
        if (this.window != null) {
            this.window.toFront();
        }

        Frame f = null;
        Dialog d = null;

        if (this.window instanceof Frame) {
            f = (Frame) this.window;
        } else if (this.window instanceof Dialog) {
            d = (Dialog) this.window;
        }

        int frameState = (f != null) ? f.getExtendedState() : 0;

        if (f != null && f.isResizable()
                && ((frameState & Frame.MAXIMIZED_BOTH) == 0)
                || (d != null && d.isResizable())) {
            dragOffsetX = dragWindowOffset.x;
            dragOffsetY = dragWindowOffset.y;
            dragWidth = this.window.getWidth();
            dragHeight = this.window.getHeight();
            dragCursor = getCursor(calculateCorner(
                    this.window, dragWindowOffset.x, dragWindowOffset.y));
        }
    }

    public void mouseReleased(MouseEvent ev) {
        if (dragCursor != 0 && window != null && !window.isValid()) {
            // Some Window systems validate as you resize, others won't,
            // thus the check for validity before repainting.
            window.validate();
        }
        isMovingWindow = false;
        dragCursor = 0;
    }

    public void mouseMoved(MouseEvent ev) {
        JRootPane root = this.rootPane;

        System.out.println("Mouse moved!");

        Frame f = null;
        Dialog d = null;

        if (this.window instanceof Frame) {
            f = (Frame) this.window;
        } else if (this.window instanceof Dialog) {
            d = (Dialog) this.window;
        }

        // Update the cursor
        int cursor = getCursor(calculateCorner(this.window, ev.getX(), ev.getY()));

        if (cursor != 0 && ((f != null && (f.isResizable() &&
                (f.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0))
                || (d != null && d.isResizable()))) {
            this.window.setCursor(Cursor.getPredefinedCursor(cursor));
        } else {
            this.window.setCursor(lastCursor);
        }
    }

    private void adjust(Rectangle bounds, Dimension min, int deltaX,
                        int deltaY, int deltaWidth, int deltaHeight) {

        bounds.width += deltaWidth;
        bounds.height += deltaHeight;
        bounds.x += deltaX;
        bounds.y += deltaY;

        if (min != null) {
            if (bounds.width < min.width) {
                int correction = min.width - bounds.width;
                if (deltaX != 0) {
                    bounds.x -= correction;
                }
                bounds.width = min.width;
            }
            if (bounds.height < min.height) {
                int correction = min.height - bounds.height;
                if (deltaY != 0) {
                    bounds.y -= correction;
                }
                bounds.height = min.height;
            }
        }
    }

    public void mouseDragged(MouseEvent ev) {
        Point pt = ev.getPoint();



        if (isMovingWindow) {
            Point eventLocationOnScreen = ev.getLocationOnScreen();
            this.window.setLocation(eventLocationOnScreen.x - dragOffsetX,
                    eventLocationOnScreen.y - dragOffsetY);
        } else if (dragCursor != 0) {
            Rectangle r = this.window.getBounds();
            Rectangle startBounds = new Rectangle(r);
            Dimension min = this.window.getMinimumSize();

            switch (dragCursor) {
                case Cursor.E_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, pt.x + (dragWidth - dragOffsetX) -
                            r.width, 0);
                    break;
                case Cursor.S_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, 0, pt.y + (dragHeight - dragOffsetY) -
                            r.height);
                    break;
                case Cursor.N_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y - dragOffsetY, 0,
                            -(pt.y - dragOffsetY));
                    break;
                case Cursor.W_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0,
                            -(pt.x - dragOffsetX), 0);
                    break;
                case Cursor.NE_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y - dragOffsetY,
                            pt.x + (dragWidth - dragOffsetX) - r.width,
                            -(pt.y - dragOffsetY));
                    break;
                case Cursor.SE_RESIZE_CURSOR:
                    adjust(r, min, 0, 0,
                            pt.x + (dragWidth - dragOffsetX) - r.width,
                            pt.y + (dragHeight - dragOffsetY) -
                                    r.height);
                    break;
                case Cursor.NW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX,
                            pt.y - dragOffsetY,
                            -(pt.x - dragOffsetX),
                            -(pt.y - dragOffsetY));
                    break;
                case Cursor.SW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0,
                            -(pt.x - dragOffsetX),
                            pt.y + (dragHeight - dragOffsetY) - r.height);
                    break;
                default:
                    break;
            }
            if (!r.equals(startBounds)) {
                this.window.setBounds(r);
                // Defer repaint/validate on mouseReleased unless dynamic
                // layout is active.
                if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
                    this.window.validate();
                    //this.rootPane.repaint();
                }
            }
        }
    }

    public void mouseEntered(MouseEvent ev) {
        lastCursor = this.window.getCursor();
        mouseMoved(ev);
    }

    public void mouseExited(MouseEvent ev) {
        this.window.setCursor(lastCursor);
    }

    private int calculateCorner(Window w, int x, int y) {
        Insets insets = w.getInsets();
        int xPosition = calculatePosition(x - insets.left,
                w.getWidth() - insets.left - insets.right);
        int yPosition = calculatePosition(y - insets.top,
                w.getHeight() - insets.top - insets.bottom);

        if (xPosition == -1 || yPosition == -1) {
            return -1;
        }
        return yPosition * 5 + xPosition;
    }

    private int calculatePosition(int spot, int width) {
        if (spot < BORDER_DRAG_THICKNESS) {
            return 0;
        }
        if (spot < CORNER_DRAG_WIDTH) {
            return 1;
        }
        if (spot >= (width - BORDER_DRAG_THICKNESS)) {
            return 4;
        }
        if (spot >= (width - CORNER_DRAG_WIDTH)) {
            return 3;
        }
        return 2;
    }

    private int getCursor(int corner) {
        if (corner == -1) {
            return 0;
        }
        return cursorMapping[corner];
    }
}
