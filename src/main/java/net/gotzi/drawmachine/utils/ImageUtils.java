package net.gotzi.drawmachine.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    /**
     * Create a new BufferedImage, draw the original image on it, and return the new BufferedImage.
     *
     * @param image The image to be resized.
     * @param targetWidth The width of the resized image
     * @param targetHeight The height of the resized image
     * @return A BufferedImage object.
     */
    public static BufferedImage resizeImage(Image image, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
