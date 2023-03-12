/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

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
