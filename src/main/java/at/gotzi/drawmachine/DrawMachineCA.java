package at.gotzi.drawmachine;

import at.gotzi.drawmachine.api.Application;
import at.gotzi.drawmachine.builder.MenuBarBuilder;
import at.gotzi.drawmachine.view.menubar.GMenuBar;
import at.gotzi.drawmachine.render.Window;
import at.gotzi.drawmachine.view.paint.PaintPanel;

import javax.swing.*;
import java.awt.*;
import java.util.logging.*;


public class DrawMachineCA implements Application {

    public static Logger LOGGER;

    private GMenuBar menuBar;
    private PaintPanel paintPanel;

    public DrawMachineCA() {
        LOGGER = Logger.getLogger("main-logger");

        this.buildMenuBar();
    }

    /**
     * It creates a new window, sets the size, centers it on the screen, maximizes it, and removes the title bar
     */
    @Override
    public void start() {
        Dimension dimension = new Dimension(1200, 675);
        Window window = new Window("DrawMachine - CA");

        window.setResizeable(true);
        window.setVisible(true);
        window.setMenuBar(menuBar);
        //window.getPanel().add(paintPanel);

        window.getPanel().setBackground(Color.DARK_GRAY);
        window.getFrame().setSize(dimension);
        window.centerOnScreen();

        window.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

        //ImageIcon imageIcon = new ImageIcon("resource/logo.png");
        //window.getFrame().setIconImage(imageIcon.getImage());

        window.start();
    }

    /**
     * Build the menu bar and store it in the menuBar variable.
     */
    private void buildMenuBar() {
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder(this);
        menuBarBuilder.build();
        this.menuBar = menuBarBuilder.getResult();
    }

    @Override
    public void stop() {

    }

    public GMenuBar getMenuBar() {
        return menuBar;
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }
}