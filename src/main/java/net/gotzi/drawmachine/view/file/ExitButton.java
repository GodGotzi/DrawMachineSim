package net.gotzi.drawmachine.view.file;


import net.gotzi.drawmachine.api.Action;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class ExitButton extends JLabel {

    private Action<MouseEvent> clickAction;

    public ExitButton() {
        addListeners();
    }

    /**
     * Draw the image on the panel.
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        try {
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("exitTabPanel.png")));
            graphics2D.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.paint(g);
    }

    private void addListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction.run(e);
            }
        };

        addMouseListener(mouseAdapter);
    }

    public void setClickAction(Action<MouseEvent> clickAction) {
        this.clickAction = clickAction;
    }
}
