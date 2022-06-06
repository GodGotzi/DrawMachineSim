package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.sim.editor.SimEditorView;
import at.gotzi.drawmachine.sim.view.SimView;
import at.gotzi.drawmachine.handler.ResizeHandler;

import javax.swing.*;
import java.io.InputStream;

public class FileView extends JSplitPane {

    private final String name;

    private final SimEditorView simEditorView;
    private final SimView simView;

    public FileView(String name) {
        this.simEditorView = new SimEditorView();
        this.simView = new SimView(simEditorView);
        this.name = name;

        ResizeHandler resizeHandler = new ResizeHandler(this, this::resizeAction);
        addComponentListener(resizeHandler);
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        setTopComponent(simEditorView.getPanel());
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

    public SimEditorView getEditorPanel() {
        return simEditorView;
    }

    public SimView getSimView() {
        return simView;
    }
}