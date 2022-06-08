package at.gotzi.drawmachine.error;

import javax.swing.*;
import java.awt.*;

public class ThreadInterrupt extends RuntimeException {
    static final long serialVersionUID = -3387516993124229945L;

    public ThreadInterrupt(Component parent, String msg) {
        showErrorInfo(parent, msg);
    }

    public void showErrorInfo(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg);
    }
}
