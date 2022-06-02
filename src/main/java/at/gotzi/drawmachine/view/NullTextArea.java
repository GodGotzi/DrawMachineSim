package at.gotzi.drawmachine.view;

import javax.swing.*;

public class NullTextArea extends JTextArea {

    public NullTextArea() {
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
