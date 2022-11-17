package at.gotzi.drawmachine.sim.algorithm;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.error.PencilOutOfCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends BufferedImage {

    private final int rgb;

    public Canvas(int width, int height, int rgb) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.rgb = rgb;

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
            setPixelPoint(getWidth() - x,getHeight() - y, rgb);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            //throw new PencilOutOfCanvas(DrawMachineSim.getInstance().getWindow().getFrame(), "X: " + (getWidth() - x) + " Y: " + (getHeight() - y));
        }
    }

    /**
     * Set the pixel at (x,y) to the given color, and set the pixels around it to the same color.
     *
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @param rgb the color of the pixel
     */
    private void setPixelPoint(int x, int y, int rgb) {
        setRGB(x, y, rgb);
        if (x < getHeight())
            setRGB(x+1, y, rgb);
        if (x > 0)
            setRGB(x-1, y, rgb);
        if (y < getWidth())
            setRGB(x, y+1, rgb);
        if (y > 0)
            setRGB(x, y-1, rgb);
    }

}
