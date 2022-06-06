package at.gotzi.drawmachine;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas implements Runnable {
    private static boolean running = false;

    private Thread thread;
    private JFrame frame;

    private final String title;

    public Window(String title) {
        this.title = title;
        this.init();
    }

    private void init() {
        this.frame = new JFrame();
        this.frame.setTitle(title);
        this.frame.add(this);
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

    public JFrame getFrame() {
        return frame;
    }
}
