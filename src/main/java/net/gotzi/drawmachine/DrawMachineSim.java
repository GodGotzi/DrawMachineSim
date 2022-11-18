package net.gotzi.drawmachine;

import net.gotzi.drawmachine.builder.HotKeyBuilder;
import net.gotzi.drawmachine.handler.IHotKeyHandler;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.builder.MenuBarBuilder;
import net.gotzi.drawmachine.view.View;
import net.gotzi.drawmachine.view.file.NullFile;
import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.menubar.GMenuBar;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.logging.*;

public class DrawMachineSim implements Application {
    private static DrawMachineSim instance;

    public static DrawMachineSim getInstance() {
        return instance;
    }

    public static Logger LOGGER;

    private Map<String, String> config;
    private GMenuBar menuBar;

    private FileHubView fileHubView;

    private Workspace workspace;

    private View view;

    private Window window;

    private IHotKeyHandler hotKeyHandler;

    public DrawMachineSim() {
        LOGGER = Logger.getLogger("main-logger");
        instance = this;

        this.loadConfig();
        this.buildMenuBar();
        this.buildView();
        this.buildHotKeyHandler();
    }

    /**
     * It creates a new window, sets the size, centers it on the screen, maximizes it, and removes the title bar
     */
    @Override
    public void start() {
        Dimension dimension = new Dimension(1200, 675);
        this.window = new Window("DrawMachine - CA", (KeyListener) hotKeyHandler);

        window.setResizeable(true);
        window.setVisible(true);
        window.setMenuBar(menuBar);
        window.getFrame().add(this.view);
        window.getFrame().setMinimumSize(new Dimension(1000, 450));

        window.getFrame().pack();
        window.getFrame().setSize(dimension);
        window.centerOnScreen();

        window.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.start();
    }

    private void buildView() {
        this.view = new View();
        this.fileHubView = view.getFileHub();
        this.workspace = view.getWorkspace();
        fileHubView.addTab("documentation.readme", new NullFile());
    }

    /**
     * Build the menu bar and store it in the menuBar variable.
     */
    private void buildMenuBar() {
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder(this);
        menuBarBuilder.build();
        this.menuBar = menuBarBuilder.getResult();
    }

    private void buildHotKeyHandler() {
        HotKeyBuilder hotKeyBuilder = new HotKeyBuilder(this);
        hotKeyBuilder.build();
        this.hotKeyHandler = hotKeyBuilder.getResult();
    }

    private void loadConfig() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        ConfigLoader configLoader = new ConfigLoader(inputStream);
        configLoader.load();
        this.config = configLoader.getResult();
    }

    public void setCursor(Cursor cursor) {
        this.window.getFrame().setCursor(cursor);
    }

    @Override
    public void stop() {

    }

    public View getView() {
        return view;
    }

    public FileHubView getFileHub() {
        return fileHubView;
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

    public IHotKeyHandler getHotKeyHandler() {
        return hotKeyHandler;
    }

    public Workspace getWorkspace() {
        return workspace;
    }
}
