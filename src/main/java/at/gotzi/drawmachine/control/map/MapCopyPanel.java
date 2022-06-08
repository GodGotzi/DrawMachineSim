package at.gotzi.drawmachine.control.map;

import at.gotzi.drawmachine.api.Action;

import javax.swing.*;
import java.awt.*;

public class MapCopyPanel extends JLabel {

    private Action<Graphics> paintAction;

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        createCanvas(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(1050, 1050, 500, 500);
        if (paintAction != null) paintAction.run(g);
    }

    private void createCanvas(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, 2100, 2100);
    }

    public void setPaintAction(Action<Graphics> paintAction) {
        this.paintAction = paintAction;
    }
}
