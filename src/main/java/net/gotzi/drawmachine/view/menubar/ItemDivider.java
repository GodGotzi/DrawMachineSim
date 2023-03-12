/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.menubar;

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
