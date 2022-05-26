package at.gotzi.drawmachine.render;

import at.gotzi.drawmachine.control.ControlComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Window extends Canvas implements Runnable {
    private static boolean running = false;

    private Thread thread;
    private JFrame frame;
    private JPanel panel;
    private GroupLayout groupLayout;
    private final List<JComponent> components;

    private final String title;

    public Window(String title) {
        this.title = title;
        this.components = new LinkedList<>();

        this.init();
    }

    private void init() {
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);
        this.frame.setTitle(title);
        this.frame.add(this);
        this.frame.add(panel);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setResizeable(boolean b) {
        this.frame.setResizable(b);
    }

    public void setVisible(boolean b) {
        this.frame.setVisible(b);
    }

    @Override
    public void run() {
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "at.gotzi.gui.Window");

    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void centerOnScreen() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.frame.setJMenuBar(menuBar);
    }

    public void addComponent(JComponent jComponent) {
        if (jComponent instanceof ControlComponent cc) cc.sizing(groupLayout);
        this.panel.add(jComponent);
        components.add(jComponent);
    }

    public void removeComponent(JComponent jComponent) {
        this.panel.remove(jComponent);
        components.remove(jComponent);
    }

    public List<JComponent> getComponents() {
        return Collections.unmodifiableList(components);
    }

    public GroupLayout getGroupLayout() {
        return groupLayout;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }
}
