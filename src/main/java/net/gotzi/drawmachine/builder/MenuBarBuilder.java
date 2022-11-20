package net.gotzi.drawmachine.builder;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.menubar.actions.NewModeFileAction;
import net.gotzi.drawmachine.menubar.actions.OpenWorkspaceAction;
import net.gotzi.drawmachine.menubar.GMenu;
import net.gotzi.drawmachine.menubar.GMenuBar;
import net.gotzi.drawmachine.menubar.ItemDivider;

import javax.swing.*;
import java.awt.*;

public class MenuBarBuilder extends Builder<GMenuBar> {

    private final DrawMachineSim drawMachineSim;
    private GMenuBar menuBar;

    public MenuBarBuilder(DrawMachineSim drawMachineSim) {
        this.drawMachineSim = drawMachineSim;
    }

    /**
     * > The function builds the menu bar
     */
    @Override
    public void build() {
        GMenuBar menuBar = new GMenuBar();

        /*
        Logo logo = new Logo();
        logo.setUnClickable()
        menuBar.add(logo);
         */

        UIManager.put("Menu.selectionBackground", Color.LIGHT_GRAY);
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("Menu.background", Color.LIGHT_GRAY);
        UIManager.put("Menu.foreground", Color.BLACK);
        UIManager.put("MenuItem.selectionBackground", Color.LIGHT_GRAY);
        UIManager.put("MenuItem.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.background", new Color(88, 88, 88));
        UIManager.put("MenuItem.foreground", Color.WHITE);
        UIManager.put("Menu.opaque", false);

        this.buildMenu00(menuBar);
        this.buildMenu01(menuBar);
        this.buildMenu04(menuBar);
        this.buildMenu05(menuBar);

        this.menuBar = menuBar;
        setSuccessful(true);
    }

    /**
     * "Create a menu called 'File' and add it to the menu bar."
     *
     * The first line creates a new menu called 'File'. The next four lines create four menu items and add them to the
     * menu. The last line adds the menu to the menu bar
     *
     * @param menuBar The GMenuBar object that you want to add the menu to.
     */
    private void buildMenu00(GMenuBar menuBar) {
        GMenu menu00 = new GMenu("File");

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
        menuBar.add(menu00);
    }

    /**
     * * Create a new menu called "Edit" and add it to the menu bar.
     * * Create three menu items and add them to the menu
     *
     * @param menuBar The GMenuBar object that you want to add the menu to.
     */
    private void buildMenu01(GMenuBar menuBar) {
        GMenu menu01 = new GMenu("Edit");
        JMenuItem item00 = new JMenuItem("Undo");
        JMenuItem item01 = new JMenuItem("Redo");
        menu01.add(item00);
        menu01.add(item01);
        //menu01.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu01);
    }

    private void buildMenu04(GMenuBar menuBar) {
        GMenu menu04 = new GMenu("Theme");

        JMenuItem menu = new JMenuItem("Edit");
        menu04.add(menu);
        //menu04.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu04);
    }

    private void buildMenu05(GMenuBar menuBar) {
        GMenu menu04 = new GMenu("Help");
        JMenuItem item01 = new JMenuItem("Discord Gotzi#2650");
        JMenuItem item02 = new JMenuItem("Email eli.gottsbacher@gmail.com");
        menu04.add(item01);
        menu04.add(item02);
        //menu04.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu04);
    }

    @Override
    public GMenuBar getResult() {
        return this.menuBar;
    }
}
