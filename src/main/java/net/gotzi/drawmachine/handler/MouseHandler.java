package net.gotzi.drawmachine.handler;

import net.gotzi.drawmachine.DrawMachineSim;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    private final Cursor cursor;

    public MouseHandler(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().setCursor(this.cursor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        DrawMachineSim.getInstance().getWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
