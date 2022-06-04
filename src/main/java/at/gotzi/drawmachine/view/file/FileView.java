package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.sim.SimView;
import at.gotzi.drawmachine.view.ResizeHandler;

import javax.swing.*;
import java.io.InputStream;

public class FileView extends JSplitPane {

    private String name;

    private EditorPanel editorPanel;
    private SimView simView;


    //test commit

    //new
    public FileView(String name) {
        this.editorPanel = new EditorPanel();
        this.simView = new SimView();
        this.name = name;

        ResizeHandler resizeHandler = new ResizeHandler(this, this::resizeAction);
        addComponentListener(resizeHandler);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setTopComponent(editorPanel);
        setBottomComponent(simView);
        setDividerSize(1);
        setEnabled(false);
    }

    private void resizeAction(int width, int height) {
        setDividerLocation(325);
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
}