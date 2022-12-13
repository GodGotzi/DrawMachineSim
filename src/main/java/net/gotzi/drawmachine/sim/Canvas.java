package net.gotzi.drawmachine.sim;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends BufferedImage {

    private final Color color;

    public Canvas(int width, int height, Color color) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.color = color;
        this.reset();
    }

    /**
     * For every pixel in the image, set the color to white.
     */
    public void reset() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                setRGB(i, j, Color.WHITE.getRGB());
            }
        }
    }

    /**
     * "Set the pixel at the given coordinates to the current color."
     *
     * The first thing we do is check if the coordinates are within the bounds of the canvas. If they are, we set the pixel
     * to the current color. If they aren't, we throw an exception
     *
     * @param x The x coordinate of the point to be set.
     * @param y The y coordinate of the pixel.
     */
    public synchronized void setPoint(int x, int y) throws PencilOutOfCanvas {
        try {
            //System.out.println("X: " + (getWidth() - x) + " Y:" + (getHeight() - y));
            setPixelPoint(getWidth() - x,getHeight() - y, color);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new PencilOutOfCanvas(DrawMachineSim.getInstance().getWindow(), "X: " + (getWidth() - x) + " Y: " + (getHeight() - y));
        }
    }
  
    /**
     * Set the pixel at (x,y) to the given color, and set the pixels around it to the same color.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @param color the color of the pixel
     */
    private void setPixelPoint(int x, int y, Color color) {
        setRGB(x, y, color.getRGB());
        if (x < getHeight())
            setRGB(x+1, y, color.getRGB());
        if (x > 0)
            setRGB(x-1, y, color.getRGB());
        if (y < getWidth())
            setRGB(x, y+1, color.getRGB());
        if (y > 0)
            setRGB(x, y-1, color.getRGB());
    }

}
