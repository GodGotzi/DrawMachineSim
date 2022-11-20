package net.gotzi.drawmachine.view.file;


import net.gotzi.drawmachine.api.Action;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Objects;

public class ExitButton extends JPanel {

    private final Action<MouseEvent> clickAction;

    public ExitButton(Action<MouseEvent> clickAction) {
        this.clickAction = clickAction;
        addListeners();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();


        try {
            Image image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("exitTabPanel.png")));
            graphics2D.setColor(Color.RED);
            graphics2D.drawImage(image, 0, 0, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //super.paint(g);
    }

    private void addListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAction.run(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        };

        addMouseListener(mouseAdapter);
    }
}
