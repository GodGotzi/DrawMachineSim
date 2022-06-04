package at.gotzi.drawmachine.menubar;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.api.Buildable;
import at.gotzi.drawmachine.api.IBuilder;
import at.gotzi.drawmachine.view.menubar.*;

import javax.swing.*;

public class MenuBarBuilder extends IBuilder<GMenuBar> implements Buildable {

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

        this.buildMenu00(menuBar);

        this.buildMenu01(menuBar);

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
        item00.addActionListener(new NewFileAction());
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
