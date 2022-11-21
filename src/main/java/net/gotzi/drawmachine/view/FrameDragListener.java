package net.gotzi.drawmachine.view;

import net.gotzi.drawmachine.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameDragListener extends MouseAdapter {
    private final Window window;
    private Point mouseDownCompCoords = null;

    public FrameDragListener(Window frame) {
        this.window = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            this.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.window.centerOnScreen();
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
        this.window.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }
}
