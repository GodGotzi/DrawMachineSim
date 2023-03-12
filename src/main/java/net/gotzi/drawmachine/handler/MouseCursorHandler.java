/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.handler;

import net.gotzi.drawmachine.DrawMachineSim;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseCursorHandler extends MouseAdapter {

    private final Cursor cursor;

    public MouseCursorHandler(Cursor cursor) {
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
