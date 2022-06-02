package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.view.Resizeable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHolder extends JLabel implements Resizeable {
    private final BufferedImage bufferedImage;

    public ImageHolder(BufferedImage bufferedImage) {
        super(new ImageIcon(bufferedImage));
        this.bufferedImage = bufferedImage;
        resizeImage(800, 800);
    }

    public void resizeImage(int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        setIcon(new ImageIcon(resizedImage));
    }

    @Override
    public void updateBounds(int width, int height) {

    }
}
