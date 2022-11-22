package net.gotzi.drawmachine.view;

import net.gotzi.drawmachine.MainWindow;
import javax.swing.*;
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
            if (mainWindow.getExtendedState() == Frame.MAXIMIZED_BOTH)
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
        Rectangle r = this.mainWindow.getBounds();
        if (currCoords != null) {
            this.mainWindow
                    .setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            if (this.mainWindow.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                this.mainWindow.centerOnScreen();
                this.mainWindow.setPreferredSize(new Dimension(r.x-16, r.y-20));
                //this.mainWindow.setBounds(r.x + 8, r.y + 10, r.x-16, r.y-20);
            }
        }
    }
}