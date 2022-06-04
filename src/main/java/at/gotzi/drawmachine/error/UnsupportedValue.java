package at.gotzi.drawmachine.error;

import javax.swing.*;
import java.awt.*;

public class UnsupportedValue extends RuntimeException {

    static final long serialVersionUID = -3387516993124229948L;

    private String msg;

    public UnsupportedValue(String msg) {
        this.msg = msg;
    }

    public void showErrorInfo(Component component) {
        JOptionPane.showMessageDialog(component, this.msg);
    }
}
