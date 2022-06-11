package at.gotzi.drawmachine.view.file;

import javax.swing.*;

public class FileHubView extends JTabbedPane implements FileHub {
    public void openFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
    }

}
