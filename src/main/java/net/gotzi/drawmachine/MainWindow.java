package net.gotzi.drawmachine;

import net.gotzi.drawmachine.handler.MouseInputHandler;
import net.gotzi.drawmachine.handler.WindowResizer;
import net.gotzi.drawmachine.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame implements Runnable {
    private static boolean running = false;
    private Thread thread;
    private final String title;

    private final View view;

    private int state = -1;

    public MainWindow(String title, View view, KeyListener keyListener) {
        this.title = title;
        this.view = view;

        this.add(view);
        this.init(keyListener);
    }

    private void init(KeyListener keyListener) {
        this.setTitle(title);
        this.setUndecorated(true);

        /*
        MouseInputHandler mouseInputHandler = new MouseInputHandler(this, this.getRootPane());
        this.addMouseListener(mouseInputHandler);
        this.addMouseMotionListener(mouseInputHandler);

        this.getContentPane().addMouseListener(mouseInputHandler);
        this.getContentPane().addMouseMotionListener(mouseInputHandler);

        this.view.addMouseListener(mouseInputHandler);
        this.view.addMouseMotionListener(mouseInputHandler);

         */

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);

        this.pack();
        setLocationRelativeTo(null);
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "net.gotzi.gui.Window");
    }

    public void setView(View view) {
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

    public void shrink() {
        setSize(new Dimension(getWidth() , 2));
    }
}
