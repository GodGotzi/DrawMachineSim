package at.gotzi.drawmachine.menubar;

import javax.swing.*;
import java.awt.*;

public class Logo extends JMenu {

    public Logo() {
        setText("           ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon imageIcon = new ImageIcon("resource/logo_low3_res.png");
        g2.drawImage(imageIcon.getImage(), 15, 0, imageIcon.getImageObserver());

        super.paintComponent(g);
    }

    public void setUnClickable() {
        setEnabled(false);
    }
}
