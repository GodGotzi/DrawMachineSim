package net.gotzi.drawmachine.error;

import javax.swing.*;
import java.awt.*;

public class UnsupportedValue {

    public UnsupportedValue(Component parent, String msg) {
        showErrorInfo(parent, msg);
    }

    public void showErrorInfo(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg);
    }
}
