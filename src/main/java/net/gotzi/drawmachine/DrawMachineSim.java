package net.gotzi.drawmachine;

import net.gotzi.drawmachine.builder.HotKeyBuilder;
import net.gotzi.drawmachine.handler.IHotKeyHandler;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.menubar.MenuBar;
import net.gotzi.drawmachine.view.View;
import net.gotzi.drawmachine.view.file.FileHubView;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
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

    private DesignHandler designHandler;

    public DrawMachineSim() throws IOException {
        LOGGER = Logger.getLogger("main-logger");
        instance = this;

        InputStream in = getClass().getClassLoader().getResourceAsStream("logo.PNG");
        this.logo = ImageIO.read(in);

        this.loadConfig();

        this.designHandler = new DesignHandler();
        this.designHandler.registerDesignColor(DesignColor.SECONDARY, Color.decode(config.get("designColor.secondary_hex")));
    }

    /**
     * It creates a new window, sets the size, centers it on the screen, maximizes it, and removes the title bar
     */
    @Override
    public void start() throws IOException {
        Dimension dimension = new Dimension(1200, 675);
        this.mainWindow = new MainWindow("DrawMachine - Simulation V1.0", (KeyListener) hotKeyHandler);

        try {
            initNimbusDesign(mainWindow);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        MenuBar menuBar = new MenuBar(this.mainWindow, this.designHandler);
        menuBar.build();
        this.menuBar = menuBar;

        this.buildHotKeyHandler();
        this.buildView();

        mainWindow.setIconImage(this.logo);
        mainWindow.setResizable(true);
        mainWindow.setJMenuBar(menuBar);
        mainWindow.add(this.view);

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
        this.view = new View(this.designHandler);
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

    public DesignHandler getDesignHandler() {
        return designHandler;
    }

    public static void initNimbusDesign(Window window) throws UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            UIManager.put("control", new Color(128, 128, 128));
            UIManager.put("info", new Color(128, 128, 128));
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
            UIManager.put("text", new Color(230, 230, 230));
            SwingUtilities.updateComponentTreeUI(window);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Nimbus: Unsupported Look and feel!");
        }

    }
}
