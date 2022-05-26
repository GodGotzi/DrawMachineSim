package at.gotzi.drawmachineapp.control;

import at.gotzi.drawmachineapp.api.MouseAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonMouseAdapter extends MouseAdapter {
    
    private final JComponent component;
    private final Color colorClick;
    private final Color colorOver;
    private final Color color;
    private boolean over = false;

    private MouseAction pressed;
    private MouseAction released;
    private MouseAction entered;
    private MouseAction exited;
    
    public ButtonMouseAdapter(JComponent component, Color colorClick, Color colorOver, Color color) {
        this.component = component;
        this.colorClick = colorClick;
        this.colorOver = colorOver;
        this.color = color;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.component.setBackground(this.colorClick);
        pressed.run(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (over)
            this.component.setBackground(this.colorOver);
        else this.component.setBackground(color);
        released.run(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.component.setBackground(this.colorOver);
        over = true;
        entered.run(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.component.setBackground(this.color);
        over = true;
        exited.run(e);
    }

    public void setEntered(MouseAction entered) {
        this.entered = entered;
    }

    public void setExited(MouseAction exited) {
        this.exited = exited;
    }

    public void setPressed(MouseAction pressed) {
        this.pressed = pressed;
    }

    public void setReleased(MouseAction released) {
        this.released = released;
    }
}
