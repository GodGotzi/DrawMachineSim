package at.gotzi.drawmachine.sim;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimRenderer implements Renderer {

    private final Dimension paperDimension;

    private final BufferedImage paper;

    public SimRenderer(Dimension dimension) {
        this.paperDimension = dimension;
        this.paper = new BufferedImage(paperDimension.width, paperDimension.height, BufferedImage.TYPE_INT_RGB);
        paintPaper();
    }

    public void paintPaper() {
        Graphics2D graphics2D = paper.createGraphics();
        createCanvas(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillOval(1050, 1050, 50, 50);
        graphics2D.dispose();
    }

    private void createCanvas(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, paperDimension.width, paperDimension.height);
    }

    public BufferedImage getPaper() {
        return paper;
    }

    @Override
    public void render() {

    }

    public void stop() {

    }
}
