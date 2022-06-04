package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.sim.SimView;
import at.gotzi.drawmachine.view.Resizeable;

import javax.swing.*;
import java.io.InputStream;

public class FileView extends JSplitPane implements Resizeable {

    private String name;

    private EditorPanel editorPanel;
    private SimView simView;


    //test commit

    //new
    public FileView(String name) {
        this.editorPanel = new EditorPanel();
        this.simView = new SimView();
        this.name = name;
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setTopComponent(editorPanel);
        setBottomComponent(simView);
        setDividerSize(1);
        setResizeWeight(0.5);
        setEnabled(false);
    }

    //existing
    public FileView(InputStream inputStream, String name) {
        this(name);
        loadExisting();
    }

    private void loadExisting() {
        //TODO
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void updateBounds(int width, int height) {
        setBounds(0, 50, width, height-50);
        editorPanel.updateBounds(width, height-50);
    }
}