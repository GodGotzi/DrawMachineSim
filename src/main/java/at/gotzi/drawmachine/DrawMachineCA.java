package at.gotzi.drawmachine;

import at.gotzi.drawmachine.api.Application;
import at.gotzi.drawmachine.data.ConfigLoader;
import at.gotzi.drawmachine.menubar.MenuBarBuilder;
import at.gotzi.drawmachine.view.NullTextArea;
import at.gotzi.drawmachine.view.Resizeable;
import at.gotzi.drawmachine.view.file.FileHub;
import at.gotzi.drawmachine.view.menubar.GMenuBar;
import at.gotzi.drawmachine.render.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.logging.*;

public class DrawMachineCA implements Application {
    private static DrawMachineCA instance;

    public static DrawMachineCA getInstance() {
        return instance;
    }

    public static Logger LOGGER;

    private Map<String, String> config;
    private GMenuBar menuBar;

    private FileHub fileHub;

    private Window window;

    public DrawMachineCA() {
        LOGGER = Logger.getLogger("main-logger");
        instance = this;
        this.loadConfig();
        this.buildMenuBar();
        this.buildTabbedPane();
    }

    /**
     * It creates a new window, sets the size, centers it on the screen, maximizes it, and removes the title bar
     */
    @Override
    public void start() {
        Dimension dimension = new Dimension(1200, 675);
        this.window = new Window("DrawMachine - CA");

        window.setResizeable(true);
        window.setVisible(true);
        window.setMenuBar(menuBar);
        window.getFrame().add(fileHub);
        window.getFrame().setMinimumSize(new Dimension(1200, 675));

        window.getFrame().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ((Resizeable) fileHub).updateBounds(window.getFrame().getWidth(), window.getFrame().getHeight());
                super.componentResized(e);
            }
        });

        window.getFrame().pack();
        window.getFrame().setSize(dimension);
        window.centerOnScreen();

        window.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

        //ImageIcon imageIcon = new ImageIcon("resource/logo.png");
        //window.getFrame().setIconImage(imageIcon.getImage());

        window.start();
    }

    private void buildTabbedPane() {
        this.fileHub = new FileHub();
        fileHub.addTab("documentation.readme", new NullTextArea());
    }

    /**
     * Build the menu bar and store it in the menuBar variable.
     */
    private void buildMenuBar() {
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder(this);
        menuBarBuilder.build();
        this.menuBar = menuBarBuilder.getResult();
    }

    private void loadConfig() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        this.config = new ConfigLoader(inputStream).getResult();
    }

    @Override
    public void stop() {

    }

    public FileHub getFileHub() {
        return fileHub;
    }

    public GMenuBar getMenuBar() {
        return menuBar;
    }

    public Window getWindow() {
        return window;
    }

    public Map<String, String> getConfig() {
        return Collections.unmodifiableMap(config);
    }
}

//yoyo checkcheck jan war hier
