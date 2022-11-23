package net.gotzi.drawmachine.view.file;

import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.control.layout.VerticalSplitLayout;
import net.gotzi.drawmachine.sim.editor.SimEditorView;
import net.gotzi.drawmachine.sim.SimView;

import java.awt.*;

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

    /**
     * This function creates a vertical split layout, and sets the size of the first component to 325 pixels.
     */
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