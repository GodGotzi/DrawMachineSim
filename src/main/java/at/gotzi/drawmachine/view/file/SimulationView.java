package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.DrawMachineCA;
import at.gotzi.drawmachine.sim.ImageHolder;
import at.gotzi.drawmachine.sim.ImageSlider;
import at.gotzi.drawmachine.view.Resizeable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SimulationView extends JSplitPane implements Resizeable {

    private ImageHolder imageHolder;
    private ImageSlider imageSlider;

    public SimulationView() {
        try {
            buildImageHolder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(Color.GRAY);
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        setDividerSize(1);
    }

    private void buildImageHolder() throws IOException {
        this.imageHolder = new ImageHolder(ImageIO.read(new File("C:\\Users\\Elias\\Desktop\\test_screen.png")));
        this.imageSlider = new ImageSlider(imageHolder);

        setTopComponent(imageHolder);
        setBottomComponent(imageSlider);
        setEnabled(false);
    }

    public void off() {
        DrawMachineCA.LOGGER.info("disabled");
        setVisible(false);
    }

    public void on() {
        DrawMachineCA.LOGGER.info("enabled");
        setVisible(true);
    }

    @Override
    public void updateBounds(int width, int height) {
        int newWidth = width > 500 ? width/3*2 : getWidth();
        int newHeight = height;
        setBounds(width > 500 ? width/3 : getY(), 0,newWidth, newHeight);
        imageHolder.updateBounds(newWidth, newHeight);
        imageSlider.updateBounds(newWidth, newHeight);
    }
}
