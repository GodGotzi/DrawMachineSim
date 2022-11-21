package net.gotzi.drawmachine;

import net.gotzi.drawmachine.view.ComponentResizer;
import net.gotzi.drawmachine.view.FrameDragListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame implements Runnable {
    private static boolean running = false;
    private Thread thread;
    private final String title;

    private ComponentResizer componentResizer;

    private int state = -1;

    public MainWindow(String title, KeyListener keyListener) {
        this.title = title;
        this.init(keyListener);
    }

    private void init(KeyListener keyListener) {
        this.setTitle(title);

        this.setUndecorated(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);

        this.pack();
        setLocationRelativeTo(null);

        addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(ComponentEvent arg0) {
            }

            @Override
            public void componentResized(ComponentEvent arg0) {
                if (state != -1) {
                    setExtendedState(state); //Restore the state.
                    state = -1; //If it is not back to -1, window won't be resized properly by OS.
                }
            }

            @Override
            public void componentMoved(ComponentEvent arg0) {
            }

            @Override
            public void componentHidden(ComponentEvent arg0) {
            }
        });
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

    public void shrink() {
        setSize(new Dimension(getWidth() , 2));
    }
}
