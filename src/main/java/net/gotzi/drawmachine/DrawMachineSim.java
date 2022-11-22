package net.gotzi.drawmachine;

import net.gotzi.drawmachine.builder.HotKeyBuilder;
import net.gotzi.drawmachine.handler.IHotKeyHandler;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.menubar.MenuBar;
import net.gotzi.drawmachine.view.View;
import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
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

    private final Image logo;

    private Map<String, String> config;
    private MenuBar menuBar;

    private FileHubView fileHubView;

    private Workspace workspace;

    private View view;

    private MainWindow mainWindow;

    private IHotKeyHandler hotKeyHandler;

    public DrawMachineSim() throws IOException {
        LOGGER = Logger.getLogger("main-logger");
        instance = this;

        InputStream in = getClass().getClassLoader().getResourceAsStream("logo.PNG");
        this.logo = ImageIO.read(in);

        this.loadConfig();
        this.buildView();
        this.buildHotKeyHandler();
    }

    /**
     * It creates a new window, sets the size, centers it on the screen, maximizes it, and removes the title bar
     */
    @Override
    public void start() throws IOException {
        Dimension dimension = new Dimension(1200, 675);
        this.mainWindow = new MainWindow("DrawMachine - Simulation V1.0", view, (KeyListener) hotKeyHandler);

        MenuBar menuBar = new MenuBar(this.mainWindow);
        menuBar.build();
        this.menuBar = menuBar;

        mainWindow.setIconImage(this.logo);
        mainWindow.setResizable(true);
        mainWindow.setJMenuBar(menuBar);

        mainWindow.setMinimumSize(new Dimension(1000, 450));

        mainWindow.setPreferredSize(dimension);
        mainWindow.pack();
        mainWindow.centerOnScreen();
        mainWindow.setBackground(Color.LIGHT_GRAY);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        mainWindow.setVisible(true);
        mainWindow.start();
    }

    private void buildView() {
        this.view = new View();
        this.fileHubView = view.getFileHub();
        this.workspace = view.getWorkspace();
        //fileHubView.addTab("documentation.readme", new NullFile());
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
        this.mainWindow.setCursor(cursor);
    }

    @Override
    public void stop() {
        System.gc();
        System.exit(0);
        System.gc();
    }

    public View getView() {
        return view;
    }

    public FileHubView getFileHub() {
        return fileHubView;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public MainWindow getWindow() {
        return mainWindow;
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

    public Image getLogo() {
        return logo;
    }
}
