package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.api.sim.SimModeInfo;
import at.gotzi.drawmachine.control.layout.VerticalSplitLayout;
import at.gotzi.drawmachine.sim.editor.SimEditorView;
import at.gotzi.drawmachine.sim.SimView;

import javax.swing.*;
import java.io.InputStream;

public class ModeFileView extends FileView {

    private final SimEditorView simEditorView;
    private final SimView simView;

    public ModeFileView(SimModeInfo simModeInfo, String name) {
        super(name);
        this.simEditorView = new SimEditorView(simModeInfo);
        this.simView = new SimView(simEditorView);

        add(simEditorView.getPanel());
        add(simView);

        this.buildLayout();
    }

    private void buildLayout() {
        VerticalSplitLayout verticalSplitLayout = new VerticalSplitLayout(this.simEditorView.getPanel(), this.simView);
        verticalSplitLayout.setComponent1Size(325);
        setLayout(verticalSplitLayout);
    }

    public SimEditorView getEditorPanel() {
        return simEditorView;
    }

    public SimView getSimView() {
        return simView;
    }
}