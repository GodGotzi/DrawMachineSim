/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine;

import net.gotzi.drawmachine.builder.HotKeyBuilder;
import net.gotzi.drawmachine.control.DimensionConstants;
import net.gotzi.drawmachine.data.ConfigLoader;
import net.gotzi.drawmachine.handler.design.ChangeDesign;
import net.gotzi.drawmachine.handler.design.ChangeManualDesign;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.handler.hotkey.HotKeyHandler;
import net.gotzi.drawmachine.view.menubar.MenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class DrawMachineSim implements Application {
    private static DrawMachineSim instance;

    public static DrawMachineSim getInstance() {
        return instance;
    }

    /**
     * It sets the Nimbus look and feel, and then changes the colors of the UI elements to match the colors of the theme
     *
     * @param window The main window of the application.
     */
    public static void initNimbusDesign(MainWindow window, DesignHandler designHandler) throws UnsupportedLookAndFeelException {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            UIManager.put("control", new Color(128, 128, 128));
            UIManager.put("info", new Color(128, 128, 128));

            UIManager.put("nimbusBase", new Color(18, 30, 49));


            UIManager.put("nimbusAlertYellow", new Color(255, 255, 255));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(84, 84, 84));
            UIManager.put("nimbusGreen", new Color(84, 84, 84));
            UIManager.put("nimbusInfoBlue", new Color(84, 84, 84));

            UIManager.put("nimbusLightBackground", new Color(227, 227, 227));

            UIManager.put("nimbusOrange", new Color(183, 183, 183));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(84, 84, 84));
            UIManager.put("text", new Color(0, 0, 0));
            SwingUtilities.updateComponentTreeUI(window);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println("Nimbus: Unsupported Look and feel!");
        }

        designHandler.registerManualChange(JSpinner.class, component -> {
            component.setBackground(Color.WHITE);
            component.setForeground(Color.BLACK);
        });

        designHandler.getDesignColorChanges(DesignColor.SECONDARY).registerPossibleChange(color -> {
            UIManager.put("nimbusFocus", color);
            UIManager.put("nimbusGreen", color);
            SwingUtilities.updateComponentTreeUI(window);
        });

    }

    private final Image logo;

    private Map<String, String> config;
    private MenuBar menuBar;

    private View view;

    private MainWindow mainWindow;

    private final DesignHandler designHandler;

    private HotKeyHandler hotKeyHandler;

    public DrawMachineSim() throws IOException {
        instance = this;

        InputStream in = Objects.requireNonNull(
                getClass().getClassLoader().getResourceAsStream("logo.png")
        );

        this.logo = ImageIO.read(in);

        DimensionConstants.load();
        this.loadConfig();

        this.designHandler = new DesignHandler();
        this.designHandler.registerDesignColor(DesignColor.SECONDARY,
                Color.decode(config.get("designColor.secondary_hex")));
    }

    /**
     * It creates a new MenuBar, View, MainWindow, and HotKeyHandler, and then sets the MainWindow to visible
     */
    @Override
    public void start() {
        this.menuBar = new MenuBar(this.designHandler).build();
        this.view = new View(this.designHandler);

        this.mainWindow = new MainWindow(this,"DrawMachine - Simulation V1.0");
        this.hotKeyHandler = new HotKeyBuilder(this).build().getResult();

        try {
            initNimbusDesign(mainWindow, this.designHandler);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        this.mainWindow.setVisible(true);
    }

    @Override
    public void stop() {
        System.gc();
        System.exit(0);
    }

    /**
     * Load the config.properties file from the classpath and parse it into a Config object.
     */
    private void loadConfig() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        ConfigLoader configLoader = new ConfigLoader(inputStream);
        configLoader.load();
        this.config = configLoader.getResult();
    }

    public Map<String, String> getConfig() {
        return Collections.unmodifiableMap(config);
    }

    public View getView() {
        return view;
    }

    public MainWindow getWindow() {
        return mainWindow;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Image getLogo() {
        return logo;
    }

    public DesignHandler getDesignHandler() {
        return designHandler;
    }

    public HotKeyHandler getHotKeyHandler() {
        return hotKeyHandler;
    }
}
