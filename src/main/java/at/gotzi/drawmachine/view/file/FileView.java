package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.control.layout.VerticalSplitLayout;
import at.gotzi.drawmachine.sim.editor.SimEditorView;
import at.gotzi.drawmachine.sim.SimView;

import javax.swing.*;
import java.io.InputStream;

public class FileView extends JPanel {

    private final String name;

    private final SimEditorView simEditorView;
    private final SimView simView;

    public FileView(String name) {
        this.simEditorView = new SimEditorView();
        this.simView = new SimView(simEditorView);
        this.name = name;


        add(simEditorView.getPanel());
        add(simView);
        this.buildLayout();
    }

    private void buildLayout() {
        VerticalSplitLayout verticalSplitLayout = new VerticalSplitLayout(this.simEditorView.getPanel(), this.simView);
        verticalSplitLayout.setComponent1Size(325);
        setLayout(verticalSplitLayout);
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