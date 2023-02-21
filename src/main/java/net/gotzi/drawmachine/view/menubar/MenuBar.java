package net.gotzi.drawmachine.view.menubar;

import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;
import net.gotzi.drawmachine.view.menubar.actions.NewProgramAction;
import net.gotzi.drawmachine.view.menubar.actions.OpenWorkspaceAction;
import net.gotzi.drawmachine.view.menubar.actions.SaveAllProgramAction;
import net.gotzi.drawmachine.view.menubar.actions.SaveProgramAction;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {

    private final DesignHandler designHandler;
    //private final Image logo23x23;

    public MenuBar(DesignHandler designHandler) {
        this.designHandler = designHandler;

        this.setBackground(Color.GRAY);

        designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> setBorder(BorderFactory.createLineBorder(color, 2)));

        //this.logo23x23 = ImageUtils.resizeImage(DrawMachineSim.getInstance().getLogo(), 23, 23);
    }

    /**
     * It adds a menu to the menu bar and registers the menu's background and foreground colors to the design handler
     *
     * @param c The menu to add
     * @return A Menu object.
     */
    public Menu add(Menu c) {
        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> c.getPopupMenu().setBackground(color));
        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(color -> c.getPopupMenu().setForeground(color));

        c.setBackground(Color.LIGHT_GRAY);
        c.setForeground(Color.WHITE);
        c.setFont(c.getFont().deriveFont(15.0f));

        add((JMenu) c);

        return c;
    }

    public MenuBar build() {

        this.buildMenuFile();
        this.buildMenuEdit();
        this.buildMenuTheme();
        this.buildMenuHelp();

        this.add(Box.createHorizontalGlue());
        //this.add(new Logo(this.logo23x23));

        return this;
    }

    private void buildMenuFile() {
        Menu menu00 = new Menu("File");

        JMenuItem item00 = new JMenuItem("Open Workspace");
        item00.addActionListener(new OpenWorkspaceAction());
        JMenuItem item01 = new JMenuItem("Open recent Workspace");
        JMenuItem item02 = new JMenuItem("New Program");
        item02.addActionListener(new NewProgramAction());
        JMenuItem item03 = new JMenuItem("Save");
        item03.addActionListener(new SaveProgramAction());
        JMenuItem item04 = new JMenuItem("Save All");
        item04.addActionListener(new SaveAllProgramAction());

        JMenuItem item05 = new JMenuItem("Save as picture");


        menu00.add(item00);
        menu00.add(item01);
        menu00.add(item02);
        menu00.add(item03);
        menu00.add(item04);
        menu00.add(item05);
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

            designHandler.getDesignColorChanges(DesignColor.SECONDARY)
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