package at.gotzi.drawmachine.error;

import javax.swing.*;
import java.awt.*;

public class UnsupportedValue extends RuntimeException {

    static final long serialVersionUID = -3387516993124229948L;

    public UnsupportedValue(Component parent, String msg) {
        showErrorInfo(parent, msg);
    }

    public void showErrorInfo(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg);
    }
}
