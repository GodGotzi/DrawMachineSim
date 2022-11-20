package net.gotzi.drawmachine.view.file;


import net.gotzi.drawmachine.api.Action;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExitButton extends JPanel {

    private final Action<MouseEvent> clickAction;

    public ExitButton(Action<MouseEvent> clickAction) {
        this.clickAction = clickAction;
        addListeners();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        graphics2D.setColor(Color.RED);
        graphics2D.drawLine(3, 3, width-3, height-3);
        graphics2D.drawLine(width-3, 3, 3, height-3);

        //super.paint(g);
    }

    private void addListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction.run(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        };

        addMouseListener(mouseAdapter);
    }
}
