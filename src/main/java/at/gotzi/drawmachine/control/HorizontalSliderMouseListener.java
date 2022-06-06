package at.gotzi.drawmachine.control;

import at.gotzi.drawmachine.DrawMachineSim;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HorizontalSliderMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        DrawMachineSim.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        DrawMachineSim.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
