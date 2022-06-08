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

    public void reset() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                setRGB(i, j, Color.WHITE.getRGB());
            }
        }
    }

    public synchronized void setPoint(int x, int y) throws PencilOutOfCanvas {
        try {
            setPixelPoint(getWidth() - x, getHeight() - y, rgb);
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new PencilOutOfCanvas(DrawMachineSim.getInstance().getWindow().getFrame());
        }
    }

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
