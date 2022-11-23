package net.gotzi.drawmachine;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final String title;

    public MainWindow(DrawMachineSim drawMachineSim, String title) {
        this.title = title;

        this.init(drawMachineSim);
    }

    /**
     * This function initializes the JFrame with the title, icon, menu bar, and view
     *
     * @param drawMachineSim The DrawMachineSim object that is being used to run the program.
     */
    private void init(DrawMachineSim drawMachineSim) {
        this.setTitle(title);

        Dimension dimension = new Dimension(1200, 675);
        this.setIconImage(drawMachineSim.getLogo());
        this.setResizable(true);
        this.setJMenuBar(drawMachineSim.getMenuBar());
        this.add(drawMachineSim.getView());

        this.setMinimumSize(new Dimension(1000, 450));

        this.centerOnScreen();
        this.setPreferredSize(dimension);
        this.setBackground(Color.LIGHT_GRAY);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
    
    /**
     * Center the window on the screen.
     */
    public void centerOnScreen() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
}
