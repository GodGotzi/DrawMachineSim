package at.gotzi.drawmachine.sim;

import javax.swing.*;
import java.awt.*;

public class SimRenderer extends JPanel implements Renderer {

    private final Dimension paperDimension;
    public SimRenderer(Dimension dimension) {
        setBackground(Color.WHITE);
        this.paperDimension = dimension;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        createCanvas(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(1050, 1050, 500, 500);
    }

    private void createCanvas(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, paperDimension.width, paperDimension.height);
    }

    @Override
    public void render() {

    }

    public void stop() {

    }
}
