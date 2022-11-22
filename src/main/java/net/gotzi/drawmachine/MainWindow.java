package net.gotzi.drawmachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame implements Runnable {
    private static boolean running = false;
    private Thread thread;
    private final String title;

    public MainWindow(String title) {
        this.title = title;

        this.init();
    }

    private void init() {
        this.setTitle(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setFocusableWindowState(true);

        this.pack();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "net.gotzi.gui.Window");
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static boolean isRunning() {
        return running;
    }

    public void centerOnScreen() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    @Override
    public void run() {
        setVisible(true);
    }
}
