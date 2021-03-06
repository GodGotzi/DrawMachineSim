package at.gotzi.drawmachine.error;

import javax.swing.*;
import java.awt.*;

public class PencilOutOfCanvas extends Throwable {

    static final long serialVersionUID = -3387516993124229948L;

    public PencilOutOfCanvas(Component parent) {
        showErrorInfo(parent, "Pencil is out of Canvas (Machine Crash!!!)");
    }

    public void showErrorInfo(Component component, String msg) {
        JOptionPane.showMessageDialog(component, msg);
    }

}
