package net.gotzi.drawmachine.view.hub.sim;

import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.control.layout.VerticalSplitLayout;
import net.gotzi.drawmachine.sim.editor.SimEditorView;
import net.gotzi.drawmachine.sim.SimView;
import net.gotzi.drawmachine.view.hub.FileView;

import java.io.File;

public class SimProgramFileView extends FileView<SimProgramInfo> {

    private final SimEditorView simEditorView;
    private final SimView simView;

    public SimProgramFileView(SimProgramInfo simProgramInfo, String name, File file) {
        super(name, "dmsp", file);
        this.simEditorView = new SimEditorView(simProgramInfo);
        this.simView = new SimView(simEditorView);

        add(simEditorView.getView());
        add(simView);

        this.buildLayout();
    }

    /**
     * This function creates a vertical split layout, and sets the size of the first component to 325 pixels.
     */
    private void buildLayout() {
        VerticalSplitLayout verticalSplitLayout = new VerticalSplitLayout(
                this.simEditorView.getView(),
                this.simView);
        verticalSplitLayout.setComponent1Size(325);
        setLayout(verticalSplitLayout);
    }

    public SimEditorView getEditorPanel() {
        return simEditorView;
    }

    public SimView getSimView() {
        return simView;
    }

    @Override
    public SimProgramInfo getObjectToSave() {
        return this.simEditorView.getNewSimProgramInfo();
    }
}