package net.gotzi.drawmachine.menubar;

import net.gotzi.drawmachine.DrawMachineSim;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class Logo extends JMenu {

    private final Image logo23x23;

    public Logo(Image logo23x23) {
        this.logo23x23 = logo23x23;

        setText("      ");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(logo23x23, 5, 1, null);

        super.paintComponent(g);
    }

    public void setUnClickable() {
        setEnabled(false);
    }
}
