package net.gotzi.drawmachine.menubar;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.MainWindow;
import net.gotzi.drawmachine.handler.MouseInputHandler;
import net.gotzi.drawmachine.handler.design.ChangeDesign;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.menubar.actions.NewModeFileAction;
import net.gotzi.drawmachine.menubar.actions.OpenWorkspaceAction;
import net.gotzi.drawmachine.utils.ImageUtils;
import net.gotzi.drawmachine.view.FrameDragListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class MenuBar extends JMenuBar {

    private final MainWindow mainWindow;

    private final DesignHandler designHandler;
    //private final Image logo23x23;

    public MenuBar(MainWindow mainWindow, DesignHandler designHandler) {
        this.mainWindow = mainWindow;
        this.designHandler = designHandler;

        setBackground(Color.GRAY);

        designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> setBorder(BorderFactory.createLineBorder(color, 2)));

        //this.logo23x23 = ImageUtils.resizeImage(DrawMachineSim.getInstance().getLogo(), 23, 23);
    }

    @Override
    public JMenu add(JMenu c) {

        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> c.getPopupMenu().setBackground(color));
        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> c.getPopupMenu().setForeground(color));

        c.setBackground(Color.LIGHT_GRAY);
        c.setForeground(Color.WHITE);
        c.setFont(c.getFont().deriveFont(15.0f));

        return super.add(c);
    }

    public void build() {

        /*
        Logo logo = new Logo(this.logo23x23);
        logo.setUnClickable();
        this.add(logo);


         */
        //MouseInputHandler mouseInputHandler = new MouseInputHandler(this.mainWindow, this.mainWindow.getRootPane());
        //this.addMouseListener(mouseInputHandler);
        //this.addMouseMotionListener(mouseInputHandler);

        this.buildMenuFile();
        this.buildMenuEdit();
        this.buildMenuTheme();
        this.buildMenuHelp();

        this.add(Box.createHorizontalGlue());
    }

    private void buildMenuFile() {
        Menu menu00 = new Menu("File");

        JMenuItem item00 = new JMenuItem("Open Workspace");
        item00.addActionListener(new OpenWorkspaceAction());
        JMenuItem item01 = new JMenuItem("Open recent Workspace");
        JMenuItem item02 = new JMenuItem("New Mode/File");
        item02.addActionListener(new NewModeFileAction());
        JMenuItem item03 = new JMenuItem("Save");
        JMenuItem item04 = new JMenuItem("Save as picture");
        menu00.add(item00);
        menu00.add(item01);
        menu00.add(item02);
        menu00.add(item03);
        menu00.add(item04);
        //menu00.add(ItemDivider.getDefaultItemDivider());
        add(menu00);
    }

    private void buildMenuEdit() {
        Menu menu01 = new Menu("Edit");
        JMenuItem item00 = new JMenuItem("Undo");
        JMenuItem item01 = new JMenuItem("Redo");
        menu01.add(item00);
        menu01.add(item01);
        //menu01.add(ItemDivider.getDefaultItemDivider());
        this.add(menu01);
    }

    private void buildMenuTheme() {
        Menu menu04 = new Menu("Settings");

        JMenuItem menu = new JMenuItem("Design Color");
        menu.addActionListener(e -> {
            Color initialColor = this.designHandler
                    .getDesignColorChanges(DesignColor.SECONDARY).getDefaultColor();
            Color color = JColorChooser.showDialog(this,"Select a color", initialColor);
            this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                    .redesignAll(color);
        });

        menu04.add(menu);

        this.add(menu04);
    }

    private void buildMenuHelp() {
        Menu menu04 = new Menu("Help");
        JMenuItem item01 = new JMenuItem("Discord Gotzi#2650");
        JMenuItem item02 = new JMenuItem("Email eli.gottsbacher@gmail.com");
        menu04.add(item01);
        menu04.add(item02);
        //menu04.add(ItemDivider.getDefaultItemDivider());
        this.add(menu04);
    }
}