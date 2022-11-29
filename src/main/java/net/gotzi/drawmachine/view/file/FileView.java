package net.gotzi.drawmachine.view.file;

import javax.swing.*;
import java.io.File;

public class FileView extends JPanel {
    private final String name;

    private final File file;

    public FileView(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    @Override
    public String getName() {
        return name;
    }
}
