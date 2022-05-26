package at.gotzi.drawmachineapp.view.menubar;

import javax.swing.*;

public class ItemDivider extends JMenuItem {

    public static ItemDivider getDefaultItemDivider() {
        return new ItemDivider(3.5f, "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
    }

    public ItemDivider(float fontSize, String msg) {
        setText(msg);
        setFont(getFont().deriveFont(fontSize));
        setEnabled(false);
    }

}
