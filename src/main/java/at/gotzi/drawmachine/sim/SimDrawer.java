package at.gotzi.drawmachine.sim;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimDrawer  {

    private BufferedImage bufferedImage;

    public SimDrawer() {
        this.bufferedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        createGraphics();
    }

    private void createGraphics() {
        Graphics2D graphics2D = this.bufferedImage.createGraphics();
        graphics2D.drawRect(50, 50, 100, 100);
    }
}
