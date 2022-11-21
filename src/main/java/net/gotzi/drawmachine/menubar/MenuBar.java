package net.gotzi.drawmachine.menubar;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.MainWindow;
import net.gotzi.drawmachine.menubar.actions.NewModeFileAction;
import net.gotzi.drawmachine.menubar.actions.OpenWorkspaceAction;
import net.gotzi.drawmachine.utils.ImageUtils;
import net.gotzi.drawmachine.view.FrameDragListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuBar extends JMenuBar {

    private final MainWindow mainWindow;
    private final Image logo23x23;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(new Color(126, 60, 183), 2));

        FrameDragListener frameDragListener = new FrameDragListener(mainWindow);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

        this.logo23x23 = ImageUtils.resizeImage(DrawMachineSim.getInstance().getLogo(), 23, 23);
    }

    @Override
    public JMenu add(JMenu c) {
        c.getPopupMenu().setBackground(new Color(126, 60, 183));
        c.getPopupMenu().setForeground(new Color(126, 60, 183));
        c.setBackground(Color.LIGHT_GRAY);
        c.setForeground(Color.WHITE);
        c.setFont(c.getFont().deriveFont(15.0f));

        return super.add(c);
    }

    public void build() {

        Logo logo = new Logo(this.logo23x23);
        logo.setUnClickable();
        this.add(logo);

        UIManager.put("Menu.selectionBackground", Color.LIGHT_GRAY);
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("Menu.background", Color.LIGHT_GRAY);
        UIManager.put("Menu.foreground", Color.BLACK);
        UIManager.put("MenuItem.selectionBackground", Color.LIGHT_GRAY);
        UIManager.put("MenuItem.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.background", new Color(88, 88, 88));
        UIManager.put("MenuItem.foreground", Color.WHITE);
        UIManager.put("Menu.opaque", false);

        this.buildMenuFile();
        this.buildMenuEdit();
        this.buildMenuTheme();
        this.buildMenuHelp();

        this.add(Box.createHorizontalGlue());
        this.buildMenuMinimize();
        this.buildMenuResize();
        this.buildMenuClose();
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
        Menu menu04 = new Menu("Theme");

        JMenuItem menu = new JMenuItem("Edit");
        menu04.add(menu);
        //menu04.add(ItemDivider.getDefaultItemDivider());
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

    private void buildMenuMinimize() {
        Menu minimize = new Menu(" ̶");

        minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainWindow.setExtendedState(Frame.ICONIFIED);
            }
        });

        this.add(minimize);
    }

    private void buildMenuResize() {
        Menu resize = new Menu("□");

        resize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int state = mainWindow.getExtendedState();
                if (state == Frame.MAXIMIZED_BOTH)
                    mainWindow.setExtendedState(Frame.NORMAL);
                else mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
            }
        });

        //menu04.add(ItemDivider.getDefaultItemDivider());
        this.add(resize);
    }

    private void buildMenuClose() {
        Menu close = new Menu("X");
        close.addChangeListener(e ->
                DrawMachineSim.getInstance().stop()
        );

        this.add(close);
    }
}