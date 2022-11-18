package net.gotzi.drawmachine.view.file;

import javax.swing.*;

public class NullFile extends JTextArea {

    public NullFile() {
        setEditable(false);
        setDoc(new String[]{
                "",
                "",
                "DOCUMENTATION DRAW_MACHINE - ControlApp",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        });
    }

    private void setDoc(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) stringBuilder.append(s).append("\n");
        setText(stringBuilder.toString());
    }
}
