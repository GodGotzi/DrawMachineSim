package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.view.Resizeable;

import javax.swing.*;
import java.io.InputStream;

public class FileView extends JSplitPane implements Resizeable {

    private String name;

    private EditorPanel editorPanel;
    private SimulationView simulationView;

    //new
    public FileView(String name) {
        this.editorPanel = new EditorPanel();
        this.simulationView = new SimulationView();
        this.name = name;
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setTopComponent(editorPanel);
        setBottomComponent(simulationView);
        setDividerSize(1);
        setResizeWeight(0.334);
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
        simulationView.updateBounds(width, height-50);
    }
}
