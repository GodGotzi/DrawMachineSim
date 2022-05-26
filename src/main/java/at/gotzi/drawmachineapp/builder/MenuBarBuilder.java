package at.gotzi.drawmachineapp.builder;

import at.gotzi.drawmachineapp.DrawMachineApp;
import at.gotzi.drawmachineapp.api.Buildable;
import at.gotzi.drawmachineapp.view.menubar.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBarBuilder extends IBuilder<GMenuBar> implements Buildable {

    private final DrawMachineApp drawMachineApp;
    private GMenuBar menuBar;

    public MenuBarBuilder(DrawMachineApp drawMachineApp) {
        this.drawMachineApp = drawMachineApp;
    }

    /**
     * > The function builds the menu bar
     */
    @Override
    public void build() {
        GMenuBar menuBar = new GMenuBar();
        Logo logo = new Logo();
        logo.setUnClickable();

        menuBar.add(logo);

        this.buildMenu00(menuBar);

        this.buildMenu01(menuBar);

        this.buildMenu02(menuBar);

        this.buildMenu04(menuBar);

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
        JMenuItem item00 = new JMenuItem("New");
        JMenuItem item01 = new JMenuItem("Open");
        JMenuItem item02 = new JMenuItem("Open Recent");
        JMenuItem item03 = new JMenuItem("Save");
        JMenuItem item04 = new JMenuItem("Save as");
        menu00.add(item00);
        menu00.add(item01);
        menu00.add(item02);
        menu00.add(item03);
        menu00.add(item04);
        menu00.add(ItemDivider.getDefaultItemDivider());
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
        menu01.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu01);
    }

    /**
     * "Builds the second menu, which is called 'View', and adds it to the menu bar."
     *
     * The first line of the function is a comment. The second line creates a new GMenu object, which is a menu. The third
     * line creates a new JMenuItem object, which is a menu item. The fourth line sets the action of the menu item. The
     * fifth line creates another menu item. The sixth line sets the action of the second menu item. The seventh line
     * creates a third menu item. The eighth line adds the first menu item to the menu. The ninth line adds the second menu
     * item to the menu. The tenth line adds the third menu item to the menu. The eleventh line adds the menu to the menu
     * bar
     *
     * @param menuBar The GMenuBar object that you want to add the menu to.
     */
    private void buildMenu02(GMenuBar menuBar) {
        GMenu menu02 = new GMenu("View");
        JMenuItem item00 = new GMenuItem("Shadows x", true) {
            @Override
            public void performAction(ActionEvent actionEvent) {

                if (isActivated()) {
                    setText("Shadows");
                } else {
                    setText("Shadows x");
                }

            }
        };

        JMenuItem item01 = new GMenuItem("Paint-View x", true) {
            @Override
            public void performAction(ActionEvent actionEvent) {

                if (isActivated()) {
                    drawMachineApp.getPaintPanel().off();
                    setText("Paint-View");
                } else {
                    drawMachineApp.getPaintPanel().on();
                    setText("Paint-View x");
                }
            }
        };

        menu02.add(item00);
        menu02.add(item01);
        menu02.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu02);
    }

    private void buildMenu04(GMenuBar menuBar) {
        GMenu menu04 = new GMenu("Help");
        JMenuItem item01 = new JMenuItem("Discord Gotzi#2650");
        JMenuItem item02 = new JMenuItem("Email eli.gottsbacher@gmail.com");
        menu04.add(item01);
        menu04.add(item02);
        menu04.add(ItemDivider.getDefaultItemDivider());
        menuBar.add(menu04);
    }

    @Override
    public GMenuBar getResult() {
        return this.menuBar;
    }
}
