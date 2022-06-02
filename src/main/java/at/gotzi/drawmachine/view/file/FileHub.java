package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.view.Resizeable;

import javax.swing.*;
import java.awt.*;

public class FileHub extends JTabbedPane implements Resizeable {

    public FileHub() {
    }

    public void openNewFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
        fileView.updateBounds(getWidth(), getHeight());
    }

    public void openExistingFilePage(FileView fileView) {
        //TODO
    }

    @Override
    public void updateBounds(int width, int height) {
        for (Component co : getComponents()) {
            if (co instanceof Resizeable resizeable) {
                resizeable.updateBounds(width, height);
            }
        }
    }
}
