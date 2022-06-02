package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.DrawMachineCA;
import at.gotzi.drawmachine.view.Resizeable;

import javax.swing.*;
import java.awt.*;

public class ImageSlider extends JSlider implements Resizeable {

    private JLabel info;

    private final ImageHolder imageHolder;

    public ImageSlider(ImageHolder imageHolder) {
        this.imageHolder = imageHolder;

        createStandardLabels(1, 0);
        setToolTipText("Look closer +/-");
        setBackground(Color.GRAY);
        setValue(0);

        addChangeListener(e -> {
            int height = DrawMachineCA.getInstance().getWindow().getFrame().getHeight();
            imageHolder.resizeImage((int) (0.741*height+17.172*getValue()-17.172), (int) (0.741*height+17.172*getValue()-17.172));
        });

    }

    @Override
    public void updateBounds(int width, int height) {
        int mainHeight = DrawMachineCA.getInstance().getWindow().getFrame().getHeight();
        imageHolder.resizeImage((int) (0.741*mainHeight+17.172*getValue()-17.172), (int) (0.741*mainHeight+17.172*getValue()-17.172));
        setBounds(0, 0, width, 50);
    }
}
