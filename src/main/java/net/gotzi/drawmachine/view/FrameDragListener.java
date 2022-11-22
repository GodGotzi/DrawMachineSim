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
        int lastState = this.mainWindow.getExtendedState();
        int xOnScreen = mainWindow.getX() + e.getX() - mouseDownCompCoords.x;
        int yOnScreen = mainWindow.getY() + e.getY() - mouseDownCompCoords.y;

        Point location = new Point(xOnScreen, yOnScreen);


        if (lastState == Frame.MAXIMIZED_BOTH) {
            System.out.println("maximized");

            Dimension dim = new Dimension(this.mainWindow.getWidth()-100, this.mainWindow.getHeight()-100);
            this.mainWindow.setSize(dim);

            location = new Point((int)location.getX() + 50, (int)location.getY() + 50);
        }

        this.mainWindow.setLocation(location);
    }
}