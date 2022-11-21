package net.gotzi.drawmachine.view;

import net.gotzi.drawmachine.MainWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameDragListener extends MouseAdapter {
    private final MainWindow mainWindow;
    private Point mouseDownCompCoords = null;

    public FrameDragListener(MainWindow frame) {
        this.mainWindow = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            int state = mainWindow.getExtendedState();
            if (state == Frame.MAXIMIZED_BOTH)
                mainWindow.setExtendedState(Frame.NORMAL);
            else mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }

    public void mouseReleased(MouseEvent e) {
        mouseDownCompCoords = null;
    }

    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        if (currCoords != null) {
            this.mainWindow.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            mainWindow.setExtendedState(Frame.NORMAL);
            mainWindow.shrink();
        }
    }
}
