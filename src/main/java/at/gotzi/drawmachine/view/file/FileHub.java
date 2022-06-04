package at.gotzi.drawmachine.view.file;

import javax.swing.*;
import java.awt.*;

public class FileHub extends JTabbedPane {

    public FileHub() {
    }

    public void openNewFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
    }

    public void openExistingFilePage(FileView fileView) {
        //TODO
    }
}
